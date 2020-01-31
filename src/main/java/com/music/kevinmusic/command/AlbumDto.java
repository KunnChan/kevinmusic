package com.music.kevinmusic.command;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class AlbumDto {

    private Long id;
    private String photoLink;
    private String  title;
    private String genre;
    private String artist;
}
