package com.music.kevinmusic.service.impl;

import com.google.gson.Gson;
import com.music.kevinmusic.command.AlbumCommand;
import com.music.kevinmusic.command.AlbumDto;
import com.music.kevinmusic.command.AlbumPageDto;
import com.music.kevinmusic.common.CustomCommon;
import com.music.kevinmusic.domain.Album;
import com.music.kevinmusic.domain.Song;
import com.music.kevinmusic.exception.NotFoundException;
import com.music.kevinmusic.filter.QAlbum;
import com.music.kevinmusic.repository.AlbumRepository;
import com.music.kevinmusic.repository.SongRepository;
import com.music.kevinmusic.request.AlbumRequest;
import com.music.kevinmusic.request.AlbumSingleRequest;
import com.music.kevinmusic.service.AlbumService;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private Gson gson;

    public AlbumServiceImpl(AlbumRepository albumRepository, SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
        this.gson = new Gson();
    }

    @Override
    public Album getById(Long id) {

        return albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Album id not found : " + id));
    }

    @Override
    public AlbumPageDto getFilterOneQuery(AlbumSingleRequest albumSingleRequest) {
        PageRequest pageable = CustomCommon.getPageable(albumSingleRequest.getPage());
        BooleanExpression filter = getQuery(albumSingleRequest.getQuery());

        if(filter == null) return pageToDto(albumRepository.findAll(pageable));

        return pageToDto(albumRepository.findAll(filter, pageable));
    }

    private BooleanExpression getQuery(String query) {
        if(query == null) return null;

        QAlbum albumEntity = QAlbum.albumEntity;
        BooleanExpression filter = albumEntity.artist.likeIgnoreCase(query)
                .or(albumEntity.genre.likeIgnoreCase(query))
                .or(albumEntity.title.likeIgnoreCase(query));

        return filter;
    }

    @Transactional
    @Override
    public Album saveOrUpdate(AlbumCommand albumCommand) {
        Album album = new Album();
        album.setId(albumCommand.getId());
        album.setTitle(albumCommand.getTitle());
        album.setArtist(albumCommand.getArtist());
        album.setGenre(albumCommand.getGenre());
        album.setPhotoLink(albumCommand.getPhotoLink());

        Long songId = albumCommand.getSongId();
        if(null != songId && !"".equals(songId)){
            Optional<Song> songOptional = songRepository.findById(songId);
            if(songOptional.isPresent()){
                album.addSong(songOptional.get());
            }
        }

        return albumRepository.save(album);
    }

    @Transactional
    @Override
    public AlbumPageDto getFilter(AlbumRequest albumRequest) {

        PageRequest pageable = CustomCommon.getPageable(albumRequest.getPage());
        List<BooleanExpression> filters = getQuery(albumRequest);

        if(filters.isEmpty()){
            return pageToDto(albumRepository.findAll(pageable));
        }

        BooleanExpression filterExpression = filters.get(0);
        for (int i = 1; i <= filters.size() - 1; ++i) {
            filterExpression = filterExpression.and(filters.get(i));
        }
        return pageToDto(albumRepository.findAll(filterExpression, pageable));
    }

    private List<BooleanExpression> getQuery(AlbumRequest albumRequest) {
        QAlbum albumEntity = QAlbum.albumEntity;
        List<BooleanExpression> filters = new ArrayList<>();

        Long id = albumRequest.getId();
        if(id != null && !"".equals(id)){
            filters.add(albumEntity.id.eq(id));
        }

        String title = albumRequest.getTitle();
        if(CustomCommon.isNotNull(title)){
            filters.add(albumEntity.title.likeIgnoreCase(title));
        }

        String genre = albumRequest.getGenre();
        if(CustomCommon.isNotNull(genre)){
            filters.add(albumEntity.genre.likeIgnoreCase(genre));
        }

        String artist = albumRequest.getArtist();
        if(CustomCommon.isNotNull(artist)){
            filters.add(albumEntity.artist.likeIgnoreCase(artist));
        }

        return filters;
    }

    private AlbumPageDto pageToDto(Page<Album> page){

        List<Album> content = page.getContent();
        List<AlbumDto> dtos = new ArrayList<>();
        for (Album album: content) {
            AlbumDto dto = new AlbumDto();
            dto.setId(album.getId());
            dto.setPhotoLink(album.getPhotoLink());
            dto.setTitle(album.getTitle());
            dto.setGenre(album.getGenre());
            dto.setArtist(album.getArtist());
            if(null != album.getSongs()){
                dto.setSongCount(album.getSongs().size());
            }
            dtos.add(dto);
        }

        AlbumPageDto dto = new AlbumPageDto();
        dto.setContent(dtos);
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
}
