package com.music.kevinmusic.domain.security;
/*
 * Created by kunnchan on 18/07/2020
 * package :  com.music.kevinmusic.domain.security
 */

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(exclude = {"roles"})
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "permission", unique = true)
    private String permission;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;
}
