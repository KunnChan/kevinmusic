package com.music.kevinmusic.service;

import com.music.kevinmusic.domain.Song;
import org.springframework.data.domain.Page;

public interface SongService {

    Song getSongById(Integer id);

    Page<Song> getList(int page, int size);

}
