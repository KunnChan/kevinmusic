package com.music.kevinmusic.security;
/*
 * Created by kunnchan on 18/07/2020
 * package :  com.music.kevinmusic.security
 */

import com.music.kevinmusic.domain.security.User;
import com.music.kevinmusic.enums.ActivationStatus;
import com.music.kevinmusic.exception.NotFoundException;
import com.music.kevinmusic.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(username+ " Not found !!"));

        if(user.getActivationStatus().equals(ActivationStatus.ACTIVE)) {
            return user;
        }else if(user.getActivationStatus().equals(ActivationStatus.PENDING)) {
            throw new NotFoundException( username + " User Status is Pending");
        }else if(user.getActivationStatus().equals(ActivationStatus.INACTIVE)) {
            throw new NotFoundException( username + " User Status is Inactive");
        }else if(user.getActivationStatus().equals(ActivationStatus.BLOCKED)) {
            throw new NotFoundException( username + " User Status is Block");
        }else{
            throw new NotFoundException( username + " User Status is Unactive");
        }
    }
}
