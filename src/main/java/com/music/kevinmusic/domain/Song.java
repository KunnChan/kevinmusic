package com.music.kevinmusic.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
public class Song implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DownloadLink> downloadLinks = new HashSet<>();

    private String photo_link;
    private String title;
    private String genre;
    private String artist;

    @Lob
    private String information;
    private String album;
    private String language;
    private Date uploaded_date;
    private String uploaded_by;
    private Date updated_date;
    private String updated_by;

}
