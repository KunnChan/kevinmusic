package com.music.kevinmusic.command;

import com.music.kevinmusic.domain.Role;
import com.music.kevinmusic.enums.ActivationStatus;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@ToString
public class UserCommand {

    private Long id;

    private String username;
    private String name;
    private String note;
    private String password;
    private String phone;
    private String email;

    private ActivationStatus activationStatus;
    private Set<Role> roles = new HashSet<>();
}
