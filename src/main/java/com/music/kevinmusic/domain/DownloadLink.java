package com.music.kevinmusic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
@EqualsAndHashCode(exclude = {"song"})
@NoArgsConstructor
public class DownloadLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Song song;

    private String linkUrl;
    private String name;

    public DownloadLink(String linkUrl, String name) {
        this.linkUrl = linkUrl;
        this.name = name;
    }

}
