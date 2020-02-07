package com.music.kevinmusic.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AlbumRequest {

    private Long id;
    private String title;
    private String genre;
    private String artist;
    private PageInfo page;
}
