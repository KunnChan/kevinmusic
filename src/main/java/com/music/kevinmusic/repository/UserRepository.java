package com.music.kevinmusic.repository;

import com.music.kevinmusic.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
}
