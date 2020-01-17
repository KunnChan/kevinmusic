package com.music.kevinmusic.repository;

import com.music.kevinmusic.domain.TransactionHistory;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends PagingAndSortingRepository<TransactionHistory, Long>,
        QuerydslPredicateExecutor<TransactionHistory> {
}
