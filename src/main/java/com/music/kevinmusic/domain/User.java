package com.music.kevinmusic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Data
@ToString
public class User extends StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 3, message = "*Your username must have at least 3 characters")
    @NotEmpty(message = "*Please provide your username")
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Length(min = 3, message = "*Your name must have at least 3 characters")
    @NotEmpty(message = "*Please provide your name")
    private String name;

    private String note;

    @Length(min = 5, message = "*Your phone must have at least 5 characters")
    @NotEmpty(message = "*Please provide your phone")
    @Column(unique = true)
    private String phone;

    @Length(min = 3, message = "*Your password must have at least 3 characters")
    @NotEmpty(message = "*Please provide your password")
    @JsonIgnore
    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles;

    private Double point = 0d;

}
