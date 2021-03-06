package com.music.kevinmusic.controller;

import com.music.kevinmusic.command.UserCommand;
import com.music.kevinmusic.common.CustomCommon;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.domain.security.User;
import com.music.kevinmusic.request.UserRequest;
import com.music.kevinmusic.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('order.read') OR " +
            "hasAuthority('customer.order.read') " +
            " AND @beerOrderAuthenticationManger.customerIdMatches(authentication, #id )")
    @GetMapping("/zone/user/{id}")
    public User get(@PathVariable Integer id, @RequestHeader MultiValueMap<String, String> headers){

        log.info("user id " + id);
        return userService.getUserById(id);
    }

    @GetMapping("/shield/user/username/{username}")
    public User getByUsername(@PathVariable String username, @RequestHeader MultiValueMap<String, String> headers){

        log.info("get by username " + username);
        return userService.getUserByUsername(username);
    }

    @PostMapping("/zone/userquery")
    public Page<User> get(@RequestBody UserRequest userRequest,
                          @RequestHeader MultiValueMap<String, String> headers){

        log.info("user advance query => {}", userRequest);
        return userService.getFilter(userRequest);
    }

    @PostMapping("/user/save")
    public User saveOrUpdate(@RequestBody UserCommand userCommand,
                             @RequestHeader MultiValueMap<String, String> headers){

        log.info("user saveOrUpdate => title : {}", userCommand);
        Information information = CustomCommon.getBrowserInformation(headers);
        return userService.saveOrUpdate(userCommand, information);
    }
}
