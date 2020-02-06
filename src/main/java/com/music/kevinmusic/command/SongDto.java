package com.music.kevinmusic.command;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class SongDto {


    private Long id;
    private String photoLink;
    private String downloadLinkUrl;
    private String downloadLinkName;
    private String title;
    private String genre;
    private String artist;
    private String album;
    private String language;
    private String lyrics;
    private String information;
    private int downloads;
}
