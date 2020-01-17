package com.music.kevinmusic.controller;

import com.music.kevinmusic.command.SongCommand;
import com.music.kevinmusic.common.CustomCommon;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.domain.Song;
import com.music.kevinmusic.request.SongRequest;
import com.music.kevinmusic.request.SongSingleRequest;
import com.music.kevinmusic.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/song")
@Slf4j
public class SongController {

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/{id}")
    public Song get(@PathVariable Long id, @RequestHeader MultiValueMap<String, String> headers){

        log.info("song id " + id);
        Information information = CustomCommon.getBrowserInformation(headers);
        return songService.getSongById(id, information);
    }

    @PostMapping("q")
    public Page<Song> get(@RequestBody SongSingleRequest songSingleRequest,
                          @RequestHeader MultiValueMap<String, String> headers){

        log.info("song specific query : {}, ", songSingleRequest);
        songSingleRequest.setInformation(CustomCommon.getBrowserInformation(headers));
        return songService.getFilterOneQuery(songSingleRequest);
    }

    @PostMapping("query")
    public Page<Song> get(@RequestBody SongRequest songRequest,
                          @RequestHeader MultiValueMap<String, String> headers){

        log.info("song advance query => {}", songRequest);
        songRequest.setInformation(CustomCommon.getBrowserInformation(headers));
        return songService.getFilter(songRequest);
    }

    @PostMapping("save")
    public Song saveOrUpdate(@RequestBody SongCommand songCommand,
                             @RequestHeader MultiValueMap<String, String> headers){

        log.info("song saveOrUpdate => title : {}", songCommand);
        Information information = CustomCommon.getBrowserInformation(headers);
        return songService.saveOrUpdate(songCommand, information);
    }
}
