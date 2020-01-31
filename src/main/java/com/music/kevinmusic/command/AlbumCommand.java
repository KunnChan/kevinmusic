package com.music.kevinmusic.command;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
public class AlbumCommand {

    private Long id;
    private String photoLink;
    private String title;
    private String genre;
    private String artist;
    private String album;
    private String language;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    List<SongCommand> songs = new ArrayList<>();
}
