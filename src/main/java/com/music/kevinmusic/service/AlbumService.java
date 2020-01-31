package com.music.kevinmusic.service;

import com.music.kevinmusic.command.AlbumCommand;
import com.music.kevinmusic.command.AlbumPageDto;
import com.music.kevinmusic.domain.Album;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.request.AlbumSingleRequest;

public interface AlbumService {

    Album getById(Long id, Information information);

    AlbumPageDto getFilterOneQuery(AlbumSingleRequest albumSingleRequest);

    Album saveOrUpdate(AlbumCommand albumCommand, Information information);
}
