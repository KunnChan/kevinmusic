package com.music.kevinmusic.request;

import com.music.kevinmusic.domain.Information;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRequest {

    private String username;
    private String name;
    private String note;
    private String password;

    private boolean isActive;

    private Information information;

    private PageInfo page;
}
