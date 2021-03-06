package com.music.kevinmusic.security;
/*
 * Created by kunnchan on 18/07/2020
 * package :  com.music.kevinmusic.security
 */

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class RestUrlAuthFilter extends AbstractRestAuthFilter {

    public RestUrlAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    protected String getUserName(HttpServletRequest request) {
        return request.getParameter("apiKey");
    }

    @Override
    protected String getPassword(HttpServletRequest request) {
        return request.getParameter("apiSecret");
    }
}
