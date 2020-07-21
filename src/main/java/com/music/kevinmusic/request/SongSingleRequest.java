package com.music.kevinmusic.request;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SongSingleRequest {

    private String query;
    private Long albumId;

    @Builder.Default
    private Boolean isPopular = false;
    private PageInfo page;
}
