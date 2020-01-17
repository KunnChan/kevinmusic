package com.music.kevinmusic.common;

import com.music.kevinmusic.domain.Information;
import com.music.kevinmusic.request.PageInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.MultiValueMap;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.Direction.fromString;

public class CustomCommon {

    public static PageRequest getPageable(PageInfo pageInfo) {
        String order = pageInfo.getOrderBy() == null ? "createdDate" : pageInfo.getOrderBy();
        Direction sortDirection = pageInfo.getDirection() == null ? DESC : fromString(pageInfo.getDirection());

        return PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), sortDirection, order);
    }

    public static PageRequest getPageable(PageInfo pageInfo, String defaultOrder) {
        String order = pageInfo.getOrderBy() == null ? defaultOrder : pageInfo.getOrderBy();
        Direction sortDirection = pageInfo.getDirection() == null ? DESC : fromString(pageInfo.getDirection());

        return PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), sortDirection, order);
    }

    public static Information getBrowserInformation(MultiValueMap<String, String> headers){
        String userAgent = headers.getFirst("user-agent");
        String host = headers.getFirst("host");
        String acceptLanguage = headers.getFirst("accept-language");
        String deviceName = headers.getFirst("deviceName"); // this will be samsung or ios or something
        String deviceId = headers.getFirst("deviceId");  // this will be imei
        String latLon = headers.getFirst("location");
        String deviceType = headers.getFirst("deviceType"); // this field represent MOBILE or WEB
        return new Information(userAgent, acceptLanguage, host, latLon, deviceName, deviceId, deviceType);
    }
}
