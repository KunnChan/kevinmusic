package com.music.kevinmusic.repository.security;
/*
 * Created by kunnchan on 18/07/2020
 * package :  com.music.kevinmusic.repository.security
 */


import com.music.kevinmusic.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
