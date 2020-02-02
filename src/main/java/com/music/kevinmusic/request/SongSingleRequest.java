package com.music.kevinmusic.request;

import com.music.kevinmusic.domain.Information;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SongSingleRequest {

    private String query;
    private Long albumId;
    private Boolean isPopular = Boolean.FALSE;
    private PageInfo page;

    private Information information;
}
