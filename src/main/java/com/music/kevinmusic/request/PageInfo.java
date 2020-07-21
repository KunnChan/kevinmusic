package com.music.kevinmusic.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageInfo {

    private int page;
    private int size;
    private String direction;
    private String orderBy;
}
