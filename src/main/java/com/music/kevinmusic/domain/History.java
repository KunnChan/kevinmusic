package com.music.kevinmusic.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.music.kevinmusic.enums.ModeAction;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.TIMESTAMP;

@ToString
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Lob
    private String content;

    @CreatedBy
    private String modifiedBy;

    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date modifiedDate;

    @Enumerated(STRING)
    private ModeAction action;

    public History() {
    }

    public History(Object file, ModeAction action) {
        String name = file.getClass().getName();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        this.name = name;
        this.content = gson.toJson(file);
        this.action = action;
    }
}