package com.music.kevinmusic.repository;

import com.music.kevinmusic.domain.Feedback;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends PagingAndSortingRepository<Feedback, Long>, QuerydslPredicateExecutor<Feedback> {
}
