package com.music.kevinmusic.controller;

import com.music.kevinmusic.domain.Song;
import com.music.kevinmusic.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/song")
@Slf4j
public class SongController {

    private SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }


    @GetMapping
    public Song get(@PathVariable Integer id){
        log.info("song id " + id);
        return songService.getSongById(id);
    }

    @GetMapping(name = "/list", params = { "page", "size" })
    public Page<Song> get(@RequestParam("page") int page, @RequestParam("size") int size){

        log.info("song list page : {}, size : {}", page, size);
        return songService.getList(page, size);
    }
}
