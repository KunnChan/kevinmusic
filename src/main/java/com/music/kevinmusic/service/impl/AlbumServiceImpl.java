package com.music.kevinmusic.service.impl;

import com.music.kevinmusic.command.AlbumCommand;
import com.music.kevinmusic.command.AlbumPageDto;
import com.music.kevinmusic.domain.Album;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.repository.AlbumRepository;
import com.music.kevinmusic.request.AlbumSingleRequest;
import com.music.kevinmusic.service.AlbumService;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public Album getById(Long id, Information information) {
        return null;
    }

    @Override
    public AlbumPageDto getFilterOneQuery(AlbumSingleRequest albumSingleRequest) {
        return null;
    }

    @Override
    public Album saveOrUpdate(AlbumCommand albumCommand, Information information) {
        return null;
    }
}
