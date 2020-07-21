package com.music.kevinmusic.repository.security;

import com.music.kevinmusic.domain.security.User;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer>, QuerydslPredicateExecutor<User> {

    Optional<User> findByUsername(String username);
}
