package com.music.kevinmusic.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Album extends StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String photoLink;
    private String title;
    private String genre;
    private String artist;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Song> songs = new HashSet<>();

    public void addSong(Song song) {
        songs.add(song);
        song.setAlbum(this);
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", photoLink='" + photoLink + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }

}
