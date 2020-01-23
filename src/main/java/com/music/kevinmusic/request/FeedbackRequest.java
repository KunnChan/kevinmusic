package com.music.kevinmusic.request;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class FeedbackRequest {

    private Long id;
    private Date fromDt;
    private Date toDt;
    private String emailOrphone;
    private String text;

    private PageInfo page;
}
