package com.music.kevinmusic.controller;

import com.music.kevinmusic.command.AlbumCommand;
import com.music.kevinmusic.command.AlbumPageDto;
import com.music.kevinmusic.common.CustomCommon;
import com.music.kevinmusic.domain.Album;
import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.request.AlbumRequest;
import com.music.kevinmusic.request.AlbumSingleRequest;
import com.music.kevinmusic.service.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }


    @GetMapping("/album/{id}")
    public Album get(@PathVariable Long id, @RequestHeader MultiValueMap<String, String> headers){

        log.info("album id " + id);
        Information information = CustomCommon.getBrowserInformation(headers);
        return albumService.getById(id, information);
    }

    @PostMapping("/album/q")
    public AlbumPageDto getTopAlbum(@RequestBody AlbumSingleRequest albumSingleRequest){

        log.info("get top album {} ", albumSingleRequest);
        return albumService.getFilterOneQuery(albumSingleRequest);
    }

    @PostMapping("/album/query")
    public AlbumPageDto getAlbums(@RequestBody AlbumRequest albumRequest){

        log.info("get getAlbums {} ", albumRequest);
        return albumService.getFilter(albumRequest);
    }

    @PostMapping("/shield/album/save")
    public Album saveOrUpdate(@RequestBody AlbumCommand albumCommand,
                             @RequestHeader MultiValueMap<String, String> headers){

        log.info("album saveOrUpdate => {}", albumCommand);
        return albumService.saveOrUpdate(albumCommand);
    }

}
