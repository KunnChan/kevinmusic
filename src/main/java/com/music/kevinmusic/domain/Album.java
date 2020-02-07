package com.music.kevinmusic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(id, album.id) &&
                Objects.equals(photoLink, album.photoLink) &&
                Objects.equals(title, album.title) &&
                Objects.equals(genre, album.genre) &&
                Objects.equals(artist, album.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, photoLink, title, genre, artist);
    }

}
