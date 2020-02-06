package com.music.kevinmusic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "Song")
@Table(name = "song")
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
    private String language;

    private int downloadCount;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    private Lyrics lyrics;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Album album;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DownloadLink> downloadLinks = new HashSet<>();

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reaction> reactions = new HashSet<>();

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Download> downloads = new HashSet<>();

    public void setLyrics(Lyrics lyrics) {
        this.lyrics = lyrics;
        lyrics.setSong(this);
    }

    public void addDownloadLink(DownloadLink downloadLink){
        downloadLink.setSong(this);
        this.downloadLinks.add(downloadLink);
    }

    public void addDownloadLinks(Set<DownloadLink> downloadLinks){
        for (DownloadLink downloadLink: downloadLinks) {
            addDownloadLink(downloadLink);
        }
    }

    public void removeDownloadLink(DownloadLink downloadLink){
        downloadLinks.remove(downloadLink);
        downloadLink.setSong(null);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setSong(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setSong(null);
    }

    public void addDownload(Download download) {
        downloads.add(download);
        download.setSong(this);
    }

    public void addReaction(Reaction reaction){
        reaction.setSong(this);
        this.reactions.add(reaction);
    }

    public void removeReaction(Reaction reaction){
        reactions.remove(reaction);
        reaction.setSong(null);
    }


    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", photoLink='" + photoLink + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", artist='" + artist + '\'' +
                ", information='" + information + '\'' +
                ", album='" + album + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
