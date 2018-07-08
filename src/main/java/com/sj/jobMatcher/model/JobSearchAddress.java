package com.sj.jobMatcher.model;

public class JobSearchAddress implements GeoPoint {

    private Double latitude;

    private Double longitude;

    private Double maxJobDistance;

    private LengthUnitEnum unit;

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

    public Double getMaxJobDistance() {
        return maxJobDistance;
    }

    public void setMaxJobDistance(Double maxJobDistance) {
        this.maxJobDistance = maxJobDistance;
    }

    public LengthUnitEnum getUnit() {
        return unit;
    }

    public void setUnit(LengthUnitEnum unit) {
        this.unit = unit;
    }

}
