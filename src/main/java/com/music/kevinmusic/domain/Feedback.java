package com.music.kevinmusic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@ToString
@NoArgsConstructor
public class Feedback extends StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String emailOrphone;
    private String text;

    public Feedback(String name, String emailOrphone, String text) {
        this.name = name;
        this.emailOrphone = emailOrphone;
        this.text = text;
    }

}
