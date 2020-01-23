package com.music.kevinmusic.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Notification {

    private String from;
    private String to;
    private String desc;
    private String title;

    public Notification(String desc, String title) {
        this.desc = desc;
        this.title = title;
    }
}
