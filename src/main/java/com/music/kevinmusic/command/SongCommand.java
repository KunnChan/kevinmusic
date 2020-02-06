package com.music.kevinmusic.command;

import com.music.kevinmusic.domain.DownloadLink;
import com.music.kevinmusic.enums.UserReaction;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
public class SongCommand {

    private Long id;
    private String photoLink;
    private String title;
    private String genre;
    private String artist;
    private String information;
    private String album;
    private String language;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;

    private String lyrics;
    private String userInfo;
    private String downloadLinkName;
    private String downloadLinkUrl;

    private String browserInfo;

    private String comment;
    private UserReaction userReaction = UserReaction.LIKE;

    private DownloadLink downloadLink;
    private Set<DownloadLink> downloadLinks = new HashSet<>();

}
