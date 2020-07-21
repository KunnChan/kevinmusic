package com.music.kevinmusic.security;
/*
 * Created by kunnchan on 18/07/2020
 * package :  com.music.kevinmusic.security
 */

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class RestHeaderAuthFilter extends AbstractRestAuthFilter {

    public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    protected String getUserName(HttpServletRequest request) {
        return request.getHeader("Api-Key");
    }

    @Override
    protected String getPassword(HttpServletRequest request) {
        return request.getHeader("Api-Secret");
    }
}
