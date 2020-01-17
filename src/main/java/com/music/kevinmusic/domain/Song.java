package com.music.kevinmusic.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ToString
@Entity
public class Song extends StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoLink;
    private String title;
    private String genre;
    private String artist;

    @Lob
    private String information;
    private String album;
    private String language;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    private Set<DownloadLink> downloadLinks = new HashSet<>();

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    private List<Reaction> reactions = new ArrayList<>();

}
