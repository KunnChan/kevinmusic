package com.music.kevinmusic.request;

import com.music.kevinmusic.domain.Information;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SongRequest {

    private Long id;
    private String title;
    private String genre;
    private String artist;
    private String album;
    private String language;
    private String info;

    private Information information;

    private PageInfo page;
}
