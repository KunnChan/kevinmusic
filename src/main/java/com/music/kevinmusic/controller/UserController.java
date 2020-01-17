package com.music.kevinmusic.controller;

import com.music.kevinmusic.command.UserCommand;
import com.music.kevinmusic.common.CustomCommon;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.domain.User;
import com.music.kevinmusic.request.UserRequest;
import com.music.kevinmusic.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zone/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/{id}")
    public User get(@PathVariable Long id, @RequestHeader MultiValueMap<String, String> headers){

        log.info("user id " + id);
        Information information = CustomCommon.getBrowserInformation(headers);
        return userService.getUserById(id, information);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/{username}")
    public User getByUsername(@PathVariable String username, @RequestHeader MultiValueMap<String, String> headers){

        log.info("get by username " + username);
        Information information = CustomCommon.getBrowserInformation(headers);
        return userService.getUserByUsername(username, information);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("query")
    public Page<User> get(@RequestBody UserRequest userRequest,
                          @RequestHeader MultiValueMap<String, String> headers){

        log.info("user advance query => {}", userRequest);
        userRequest.setInformation(CustomCommon.getBrowserInformation(headers));
        return userService.getFilter(userRequest);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("save")
    public User saveOrUpdate(@RequestBody UserCommand userCommand,
                             @RequestHeader MultiValueMap<String, String> headers){

        log.info("user saveOrUpdate => title : {}", userCommand);
        Information information = CustomCommon.getBrowserInformation(headers);
        return userService.saveOrUpdate(userCommand, information);
    }
}
