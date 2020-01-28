package com.music.kevinmusic.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude ={"song"})
@Entity
@NoArgsConstructor
public class Lyrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Song song;
    // in this case while we delete lyrics and not affect to Song
    // but if we delete Song, lyrics will gone.

    public Lyrics(String text) {
        this.text = text;
    }

    @Lob
    private String text;
}
