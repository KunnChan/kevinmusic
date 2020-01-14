package com.music.kevinmusic.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
public class DownloadHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long songId;
    private Long downloadLoadId;
    private Date downloadedDate;
    private String information;
}
