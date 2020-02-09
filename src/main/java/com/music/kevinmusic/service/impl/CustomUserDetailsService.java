package com.music.kevinmusic.service.impl;

import com.music.kevinmusic.domain.User;
import com.music.kevinmusic.enums.ActivationStatus;
import com.music.kevinmusic.exception.NotFoundException;
import com.music.kevinmusic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepo.findByUsername(username);
        if (!user.isPresent()) {
            throw new NotFoundException(username+ " Not found !!");
        }
        User found = user.get();
        if(found.getActivationStatus().equals(ActivationStatus.ACTIVE)) {
            return new UserDetailsImpl(found);
        }else if(found.getActivationStatus().equals(ActivationStatus.PENDING)) {
            throw new NotFoundException( username + " User Status is Pending");
        }else if(found.getActivationStatus().equals(ActivationStatus.INACTIVE)) {
            throw new NotFoundException( username + " User Status is Inactive");
        }else if(found.getActivationStatus().equals(ActivationStatus.BLOCKED)) {
            throw new NotFoundException( username + " User Status is Block");
        }else{
            throw new NotFoundException( username + " User Status is Unactive");
        }

    }
}
