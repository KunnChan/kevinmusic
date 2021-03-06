package com.music.kevinmusic.request;

import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.enums.ActivationStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRequest {

    private Integer id;
    private String username;
    private String name;
    private String note;
    private String password;
    private String phone;
    private String email;

    private ActivationStatus activationStatus;

    private Information information;

    private PageInfo page;
}
