package com.music.kevinmusic.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
public class DownloadLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    private String linkUrl;
}
