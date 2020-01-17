package com.music.kevinmusic.command;

import com.music.kevinmusic.domain.Role;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class UserCommand {

    private Long id;

    private String username;
    private String name;
    private String note;
    private String password;

    private boolean isActive;
    private List<Role> roles = new ArrayList<>();
}
