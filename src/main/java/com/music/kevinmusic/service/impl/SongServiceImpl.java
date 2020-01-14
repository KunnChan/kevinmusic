package com.music.kevinmusic.service.impl;

import com.music.kevinmusic.domain.Song;
import com.music.kevinmusic.service.SongService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl implements SongService {

    @Override
    public Song getSongById(Integer id) {
        return null;
    }

    @Override
    public Page<Song> getList(int page, int size) {
        return null;
    }
}
