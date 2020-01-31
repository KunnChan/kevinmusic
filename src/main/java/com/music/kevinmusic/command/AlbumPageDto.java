package com.music.kevinmusic.command;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
public class AlbumPageDto {

    private List<AlbumDto> content = new ArrayList<>();

    private int totalPages;
    private long totalElements;
    private Boolean last;
    private int size;
    private int number;
    private Boolean first;
    private int numberOfElements;
    private Boolean empty;
}
