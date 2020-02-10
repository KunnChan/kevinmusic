package com.music.kevinmusic.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AlbumSingleRequest {

    private String query;
    private PageInfo page;

}
