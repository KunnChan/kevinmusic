package com.music.kevinmusic.repository;

import com.music.kevinmusic.domain.Song;
import com.music.kevinmusic.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SongRepository extends PagingAndSortingRepository<Song, Integer> {
}
