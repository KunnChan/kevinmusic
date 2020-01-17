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

    private String userAgent;
    private String acceptLanguage;
    private String host;
    private String latLon;
    private String deviceName;
    private String deviceId;

    public Information(String userAgent, String acceptLanguage, String host, String latLon, String deviceName, String deviceId) {
        this.userAgent = userAgent;
        this.acceptLanguage = acceptLanguage;
        this.host = host;
        this.latLon = latLon;
        this.deviceName = deviceName;
        this.deviceId = deviceId;
    }
}
