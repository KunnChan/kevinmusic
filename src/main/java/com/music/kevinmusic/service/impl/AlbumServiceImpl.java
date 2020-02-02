package com.music.kevinmusic.service.impl;

import com.google.gson.Gson;
import com.music.kevinmusic.command.AlbumCommand;
import com.music.kevinmusic.command.AlbumDto;
import com.music.kevinmusic.command.AlbumPageDto;
import com.music.kevinmusic.common.CustomCommon;
import com.music.kevinmusic.domain.Album;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.domain.TransactionHistory;
import com.music.kevinmusic.enums.EventAction;
import com.music.kevinmusic.exception.NotFoundException;
import com.music.kevinmusic.filter.QAlbum;
import com.music.kevinmusic.repository.AlbumRepository;
import com.music.kevinmusic.repository.TransactionHistoryRepository;
import com.music.kevinmusic.request.AlbumSingleRequest;
import com.music.kevinmusic.service.AlbumService;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final TransactionHistoryRepository historyRepo;
    private Gson gson;

    public AlbumServiceImpl(AlbumRepository albumRepository, TransactionHistoryRepository historyRepo) {
        this.albumRepository = albumRepository;
        this.historyRepo = historyRepo;
        this.gson = new Gson();
    }

    @Override
    public Album getById(Long id, Information information) {

        TransactionHistory history = new TransactionHistory("Album Id : " + id, EventAction.SEARCH_ALBUM_BY_ID);
        history.setInformation(information);
        historyRepo.save(history);

        return albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Album id not found : " + id));
    }

    @Override
    public AlbumPageDto getFilterOneQuery(AlbumSingleRequest albumSingleRequest) {
        PageRequest pageable = CustomCommon.getPageable(albumSingleRequest.getPage());
        BooleanExpression filter = getQuery(albumSingleRequest.getQuery());

        TransactionHistory history = new TransactionHistory(gson.toJson(albumSingleRequest), EventAction.SEARCH_SONG_BY_SINGLE_QUERY);
        history.setInformation(albumSingleRequest.getInformation());
        historyRepo.save(history);

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

    @Override
    public Album saveOrUpdate(AlbumCommand albumCommand, Information information) {
        return null;
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
