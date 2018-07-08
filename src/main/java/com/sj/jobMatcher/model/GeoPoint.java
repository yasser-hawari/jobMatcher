package com.sj.jobMatcher.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Optional;

public interface GeoPoint extends Serializable{


    Double getLatitude();

    void setLatitude(Double latitude);

    Double getLongitude();

    void setLongitude(Double longitude);

    @JsonIgnore
    default boolean isGeoPointSet(){
        return getLatitude() != null && getLongitude() != null;
    };

}