package com.music.kevinmusic.repository;

import com.music.kevinmusic.domain.Song;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends PagingAndSortingRepository<Song, Long>, QuerydslPredicateExecutor<Song> {
}
