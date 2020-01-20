package com.music.kevinmusic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.music.kevinmusic.enums.UserReaction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(exclude = {"song"})
@NoArgsConstructor
public class Reaction extends StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private UserReaction userReaction;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Song song;

    public Reaction(UserReaction userReaction) {
        this.userReaction = userReaction;
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "id=" + id +
                ", userReaction=" + userReaction +
                '}';
    }
}
