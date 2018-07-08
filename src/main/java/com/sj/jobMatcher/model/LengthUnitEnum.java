package com.sj.jobMatcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LengthUnitEnum {
    @JsonProperty("km")
    KiloMeter,

    @JsonProperty("m")
    Meter,

    @JsonProperty("mile")
    Mile;

    public Double getConversionRateToKm(){
        switch (this) {
            case KiloMeter:
                return 1.0;
            case Meter:
                return 1000.0;
            case Mile:
                return 1.60934;
            default:
                throw new IllegalStateException("We shouldn't really reach here");
        }
    }
}