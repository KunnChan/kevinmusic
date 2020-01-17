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
        String order = pageInfo.getOrderBy() == null ? "createdAt" : pageInfo.getOrderBy();
        Direction sortDirection = pageInfo.getDirection() == null ? DESC : fromString(pageInfo.getDirection());

        return PageRequest.of(pageInfo.getPage(), pageInfo.getSize(), sortDirection, order);
    }

    public static Information getBrowserInformation(MultiValueMap<String, String> headers){
        String userAgent = headers.getFirst("user-agent");
        String host = headers.getFirst("host");
        String acceptLanguage = headers.getFirst("accept-language");
        String deviceName = headers.getFirst("deviceName");
        String deviceId = headers.getFirst("deviceId");
        String latLon = headers.getFirst("location");
        return new Information(userAgent, acceptLanguage, host, latLon, deviceName, deviceId);
    }
}
