package com.sj.jobMatcher.model;

public class Location implements GeoPoint {

    private Double latitude;

    private Double longitude;

    // Getters and Setters

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
