package com.music.kevinmusic.controller;

import com.music.kevinmusic.command.PointCommand;
import com.music.kevinmusic.common.CustomCommon;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.domain.User;
import com.music.kevinmusic.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class PointController {

    private final UserService userService;

    public PointController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/shield/user/{id}")
    public User get(@PathVariable Long id, @RequestHeader MultiValueMap<String, String> headers){

        log.info("user id " + id);
        return userService.getUserById(id);
    }

    @PostMapping("/shield/point/add")
    public User addPoint(@RequestBody PointCommand pointCommand, @RequestHeader MultiValueMap<String, String> headers){

        log.info("point add {} ", pointCommand);
        Information information = CustomCommon.getBrowserInformation(headers);
        return userService.addPoint( pointCommand, information);
    }

    @PostMapping("/shield/point/sub")
    public User subPoint(@RequestBody PointCommand pointCommand, @RequestHeader MultiValueMap<String, String> headers){

        log.info("point sub {} ", pointCommand);
        Information information = CustomCommon.getBrowserInformation(headers);
        return userService.subPoint( pointCommand, information);
    }

}
