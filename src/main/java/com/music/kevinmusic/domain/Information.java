package com.music.kevinmusic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@ToString
@Data
@Entity
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String osVersion;
    private String model;
    private String host;
    private String latLon;
    private String manufacturer;
    private String platform;
    private String uuid;
    private String deviceType;

}
