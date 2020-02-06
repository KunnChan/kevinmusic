package com.music.kevinmusic.service.impl;

import com.google.gson.Gson;
import com.music.kevinmusic.command.SongCommand;
import com.music.kevinmusic.command.SongDto;
import com.music.kevinmusic.command.SongPageDto;
import com.music.kevinmusic.common.CustomCommon;
import com.music.kevinmusic.domain.*;
import com.music.kevinmusic.enums.EventAction;
import com.music.kevinmusic.exception.NotFoundException;
import com.music.kevinmusic.filter.QSong;
import com.music.kevinmusic.repository.SongRepository;
import com.music.kevinmusic.repository.TransactionHistoryRepository;
import com.music.kevinmusic.request.SongRequest;
import com.music.kevinmusic.request.SongSingleRequest;
import com.music.kevinmusic.service.SongService;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final TransactionHistoryRepository historyRepo;
    private ModelMapper modelMapper;
    private Gson gson;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, TransactionHistoryRepository historyRepo) {
        this.songRepository = songRepository;
        this.historyRepo = historyRepo;
        modelMapper = new ModelMapper();
        gson = new Gson();
    }

    @Override
    @Transactional
    public Song getSongById(Long id, Information information) {
        TransactionHistory history = new TransactionHistory("Song Id : " + id, EventAction.SEARCH_SONG_BY_ID);
        history.setInformation(information);
        historyRepo.save(history);

        return songRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Song id not found : " + id));
    }

    @Override
    @Transactional
    public SongPageDto getFilterOneQuery(SongSingleRequest songSingleRequest) {

        PageRequest pageable;
        if(songSingleRequest.getIsPopular()){
            pageable = CustomCommon.getPageable(songSingleRequest.getPage(), "downloadCount");

        }else{
            pageable = CustomCommon.getPageable(songSingleRequest.getPage());

        }
        BooleanExpression filter = getQuery(songSingleRequest.getQuery());

        TransactionHistory history = new TransactionHistory(gson.toJson(songSingleRequest), EventAction.SEARCH_SONG_BY_SINGLE_QUERY);
        history.setInformation(songSingleRequest.getInformation());
        historyRepo.save(history);

        if(filter == null) return pageToDto(songRepository.findAll(pageable));

        return pageToDto(songRepository.findAll(filter, pageable));
    }

    @Override
    @Transactional
    public SongPageDto getFilter(SongRequest songRequest) {
        PageRequest pageable = CustomCommon.getPageable(songRequest.getPage());
        List<BooleanExpression> filters = getQuery(songRequest);

        TransactionHistory history = new TransactionHistory(gson.toJson(songRequest), EventAction.SEARCH_SONG_ADVANCE);
        history.setInformation(songRequest.getInformation());
        historyRepo.save(history);

        if(filters.isEmpty()){
            return pageToDto(songRepository.findAll(pageable));
        }

        BooleanExpression filterExpression = filters.get(0);
        for (int i = 1; i <= filters.size() - 1; ++i) {
            filterExpression = filterExpression.and(filters.get(i));
        }
        return pageToDto(songRepository.findAll(filterExpression, pageable));
    }

    @Override
    @Transactional
    public Song saveOrUpdate(SongCommand songCommand, Information information) {
        Song song = modelMapper.map(songCommand, Song.class);
        TransactionHistory history = new TransactionHistory(gson.toJson(songCommand));
        history.setInformation(information);
        if(song.getId() == null){
            // insert
            history.setEventAction(EventAction.CREATE_SONG);
        }else{
            // update
          //  Song songForUpdate = songRepository.findById(song.getId())
          ///          .orElseThrow(() -> new NotFoundException("Song id not found for update : " + song.getId()));

            history.setEventAction(EventAction.UPDATE_SONG);
        }

        historyRepo.save(history);
        return songRepository.save(song);
    }

    @Override
    @Transactional
    public Song addComment(SongCommand songCommand, Information information) {

        TransactionHistory history = new TransactionHistory(gson.toJson(songCommand), EventAction.COMMENT_ADD);
        history.setInformation(information);

        Song song = songRepository.findById(songCommand.getId())
                .orElseThrow(() -> new NotFoundException("Song id not found : " + songCommand.getId()));

        song.addComment(new Comment(songCommand.getComment()));

        historyRepo.save(history);
        return songRepository.save(song);
    }

    @Transactional
    @Override
    public Song addDownloadLinks(SongCommand songCommand, Information information) {
        TransactionHistory history = new TransactionHistory(gson.toJson(songCommand), EventAction.DOWNLOAD_LINK_ADD);
        history.setInformation(information);

        Song song = songRepository.findById(songCommand.getId())
                .orElseThrow(() -> new NotFoundException("Song id not found : " + songCommand.getId()));

        song.addDownloadLinks(songCommand.getDownloadLinks());

        historyRepo.save(history);
        return songRepository.save(song);
    }

    @Transactional
    @Override
    public Song removeDownloadLink(SongCommand songCommand, Information information) {
        TransactionHistory history = new TransactionHistory(gson.toJson(songCommand), EventAction.DOWNLOAD_LINK_REMOVE);
        history.setInformation(information);

        Song song = songRepository.findById(songCommand.getId())
                .orElseThrow(() -> new NotFoundException("Song id not found : " + songCommand.getId()));

        song.removeDownloadLink(songCommand.getDownloadLink());

        historyRepo.save(history);
        return songRepository.save(song);
    }

    @Transactional
    @Override
    public Song addSongLyric(SongCommand songCommand, Information information) {
        TransactionHistory history = new TransactionHistory(gson.toJson(songCommand), EventAction.LYRIC_UPDATE);
        history.setInformation(information);

        Song song = songRepository.findById(songCommand.getId())
                .orElseThrow(() -> new NotFoundException("Song id not found : " + songCommand.getId()));

        song.setLyrics(new Lyrics(songCommand.getLyrics()));

        historyRepo.save(history);
        return songRepository.save(song);
    }

    @Transactional
    @Override
    public Song addDownload(SongCommand songCommand, Information information) {
        TransactionHistory history = new TransactionHistory(gson.toJson(songCommand), EventAction.DOWNLOAD);
        history.setInformation(information);

        Song song = songRepository.findById(songCommand.getId())
                .orElseThrow(() -> new NotFoundException("Song id not found : " + songCommand.getId()));

        song.addDownload(new Download(songCommand.getUserInfo()));
        song.setDownloadCount(song.getDownloads().size());

        historyRepo.save(history);
        return songRepository.save(song);
    }

    @Transactional
    @Override
    public List<SongDto> getSongByAlbumId(Long albumId, Information information) {
        TransactionHistory history = new TransactionHistory("Album Id : " + albumId, EventAction.GET_SONG_BY_ALBUMID);
        history.setInformation(information);
        historyRepo.save(history);

        return toDto(songRepository.findAllByAlbumId(albumId));
    }

    @Transactional
    @Override
    public Song addReaction(SongCommand songCommand, Information information) {
        TransactionHistory history = new TransactionHistory(gson.toJson(songCommand), EventAction.REACTION_ADD);
        history.setInformation(information);

        Song song = songRepository.findById(songCommand.getId())
                .orElseThrow(() -> new NotFoundException("Song id not found : " + songCommand.getId()));

        Reaction reaction = new Reaction(songCommand.getUserReaction());
        song.addReaction(reaction);

        historyRepo.save(history);
        return songRepository.save(song);
    }

    private BooleanExpression getQuery(String query) {
        if(query == null) return null;

       QSong songQuery = QSong.songEntity;
       BooleanExpression filter = songQuery.artist.likeIgnoreCase(query)
                .or(songQuery.genre.likeIgnoreCase(query))
                .or(songQuery.language.likeIgnoreCase(query))
                .or(songQuery.title.likeIgnoreCase(query));

        return filter;
    }

    private List<BooleanExpression> getQuery(SongRequest songRequest) {
        QSong songQuery = QSong.songEntity;
        List<BooleanExpression> filters = new ArrayList<>();
        Long id = songRequest.getId();
        if(id != null && !"".equals(id)){
            filters.add(songQuery.id.eq(id));
        }

        String title = songRequest.getTitle();
        if(CustomCommon.isNotNull(title)){
            filters.add(songQuery.title.likeIgnoreCase(title));
        }

        String genre = songRequest.getGenre();
        if(CustomCommon.isNotNull(genre)){
            filters.add(songQuery.genre.likeIgnoreCase(genre));
        }

        String artist = songRequest.getArtist();
        if(CustomCommon.isNotNull(artist)){
            filters.add(songQuery.artist.likeIgnoreCase(artist));
        }

        String language = songRequest.getLanguage();
        if(CustomCommon.isNotNull(language)){
            filters.add(songQuery.language.likeIgnoreCase(language));
        }

        String info = songRequest.getInfo();
        if(CustomCommon.isNotNull(info)){
            filters.add(songQuery.information.likeIgnoreCase(info));
        }
        return filters;
    }

    private SongPageDto pageToDto(Page<Song> page){

        SongPageDto dto = new SongPageDto();
        dto.setContent(toDto(page.getContent()));
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements(page.getTotalElements());
        dto.setLast(page.isLast());
        dto.setSize(page.getSize());
        dto.setNumber(page.getNumber());
        dto.setFirst(page.isFirst());
        dto.setNumberOfElements(page.getNumberOfElements());
        dto.setEmpty(page.isEmpty());

        return dto;
    }

    private List<SongDto> toDto(List<Song> songs){
        List<SongDto> dtos = new ArrayList<>();
        for (Song song: songs) {
            SongDto dto = new SongDto();
            dto.setId(song.getId());
            dto.setPhotoLink(song.getPhotoLink());
            dto.setTitle(song.getTitle());
            dto.setGenre(song.getGenre());
            dto.setArtist(song.getArtist());
            dto.setLanguage(song.getLanguage());

            if(null != song.getAlbum()){
                dto.setAlbum(song.getAlbum().getTitle());
            }

            if(null != song.getLyrics()){
                dto.setLyrics(song.getLyrics().getText());
            }

            if(null != song.getDownloadLinks() && song.getDownloadLinks().size() > 0){
                Set<DownloadLink> downloadLinks = song.getDownloadLinks();
                DownloadLink downloadLink = downloadLinks.iterator().next();
                dto.setDownloadLinkUrl(downloadLink.getLinkUrl());
                dto.setDownloadLinkName(downloadLink.getName());

            }
            dto.setDownloads(song.getDownloadCount());
            dtos.add(dto);
        }
        return dtos;
    }

}
