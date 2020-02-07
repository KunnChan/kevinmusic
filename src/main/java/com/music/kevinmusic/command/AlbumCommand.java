package com.music.kevinmusic.command;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class AlbumCommand {

    private Long id;
    private String photoLink;
    private String title;
    private String genre;
    private String artist;
    private String album;
    private Long songId;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

}
