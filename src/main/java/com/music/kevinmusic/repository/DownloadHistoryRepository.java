package com.music.kevinmusic.repository;

import com.music.kevinmusic.domain.DownloadHistory;
import com.music.kevinmusic.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DownloadHistoryRepository extends PagingAndSortingRepository<DownloadHistory, Integer> {
}
