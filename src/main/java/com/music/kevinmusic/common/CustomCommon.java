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
        String osVersion = headers.getFirst("os-version");
        String host = headers.getFirst("host");
        String model = headers.getFirst("model");
        String manufacturer = headers.getFirst("manufacturer");
        String platform = headers.getFirst("platform");
        String uuid = headers.getFirst("uuid");
        String latLon = headers.getFirst("latlon");
        String deviceType = headers.getFirst("device-type");

        Information information = new Information();
        information.setDeviceType(deviceType);
        information.setOsVersion(osVersion);
        information.setHost(host);
        information.setModel(model);
        information.setManufacturer(manufacturer);
        information.setPlatform(platform);
        information.setUuid(uuid);
        information.setLatLon(latLon);

        return information;
    }

    public static boolean isNotNull(String str){
        if(str != null && !"".equals(str))
            return true;
        return false;
    }
}
