package com.music.kevinmusic.request;

import com.music.kevinmusic.domain.EventAction;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class TransactionHistoryRequest {

    private Long id;
    private Date fromDt;
    private Date toDt;
    private EventAction eventAction;
    private String payload;
    private String information;

    private PageInfo page;
}
