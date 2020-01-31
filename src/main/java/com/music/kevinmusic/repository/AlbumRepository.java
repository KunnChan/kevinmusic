package com.music.kevinmusic.repository;

import com.music.kevinmusic.domain.Album;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends PagingAndSortingRepository<Album, Long>, QuerydslPredicateExecutor<Album> {
}
