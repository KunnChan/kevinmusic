package com.music.kevinmusic.security;
/*
 * Created by kunnchan on 20/07/2020
 * package :  com.music.kevinmusic.security
 */

import com.music.kevinmusic.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class BeerOrderAuthenticationManger {
    public boolean customerIdMatches(Authentication authentication, UUID customerId){
        User authenticatedUser = (User) authentication.getPrincipal();

        log.debug("Auth User Customer Id: " + authenticatedUser.getCustomer().getId() + " Customer Id:" + customerId);

        return authenticatedUser.getCustomer().getId().equals(customerId);
    }
}
