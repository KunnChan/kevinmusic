package com.music.kevinmusic.service.impl;

import com.music.kevinmusic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username)
                .map(u -> new org.springframework.security.core.userdetails.User(
                        u.getUsername(),
                        u.getPassword(),
                        u.isActive(),
                        u.isActive(),
                        u.isActive(),
                        u.isActive(),
                        AuthorityUtils.createAuthorityList(
                                u.getRoles()
                                        .stream()
                                        .map(r -> "ROLE_" + r.getName().toUpperCase())
                                        .collect(Collectors.toList())
                                        .toArray(new String[] {})
                        )))
                .orElseThrow(() -> new UsernameNotFoundException("No user name with "+ username + " was found in db."));
    }
}
