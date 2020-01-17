package com.music.kevinmusic.service.impl;

import com.music.kevinmusic.domain.ActivationStatus;
import com.music.kevinmusic.domain.User;
import com.music.kevinmusic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepo.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username+ " Not found !!");
        }
        User found = user.get();
        if(found.getActivationStatus().equals(ActivationStatus.ACTIVE)) {
            return new UserDetailsImpl(found);
        }else if(found.getActivationStatus().equals(ActivationStatus.PENDING)) {
            throw new UsernameNotFoundException( username + " Not found or User Status is Pending");
        }else if(found.getActivationStatus().equals(ActivationStatus.BLOCKED)) {
            throw new UsernameNotFoundException( username + " Not found or User Status is Block");
        }else{
            throw new UsernameNotFoundException( username + " Not found or User Status is Unactive");
        }

    }
}
