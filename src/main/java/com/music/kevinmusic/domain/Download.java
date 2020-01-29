package com.music.kevinmusic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(exclude = {"song"})
@NoArgsConstructor
public class Download extends StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Download(String userInfo) {
        this.userInfo = userInfo;
    }

    @Lob
    private String userInfo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Song song;



}
