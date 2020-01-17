package com.music.kevinmusic.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PageInfo {

    private int page;
    private int size;
    private String direction;
    private String orderBy;
}
