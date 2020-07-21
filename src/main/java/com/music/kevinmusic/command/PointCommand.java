package com.music.kevinmusic.command;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PointCommand {

    private Integer userId;
    private Double point = 0d;
    private String desc;
    private Double pointAfterModify = 0d;
}
