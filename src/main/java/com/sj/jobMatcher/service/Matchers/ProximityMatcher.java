package com.sj.jobMatcher.service.Matchers;

import com.sj.jobMatcher.model.GeoPoint;
import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.LengthUnitEnum;
import com.sj.jobMatcher.model.Worker;
import com.sj.jobMatcher.service.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProximityMatcher implements Matcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProximityMatcher.class);

    private Double maxDefaultDistanceKm = 50.0;

    @Override
    public Integer getPriority() {
        return 20;
    }

    @Override
    public boolean matchJob(final Worker worker, final Job job) {

        boolean result = false;

        if(worker.getJobSearchAddress() == null || !worker.getJobSearchAddress().isGeoPointSet()
                || job.getLocation() == null || !job.getLocation().isGeoPointSet()) {
            result = true;  // if any of the important data is not set, proximity is NOT important
        } else if(worker.getJobSearchAddress() != null ){
            Optional<Double> distance = calculateDistance(worker.getJobSearchAddress(), job.getLocation());

            if(worker.getJobSearchAddress().getMaxJobDistance() == null) {

                result = maxDefaultDistanceKm >= distance.orElse(0.0); // I know we will never reach here.
            } else {
                LengthUnitEnum distanceUnit =
                         worker.getJobSearchAddress().getUnit() != null ?
                                 worker.getJobSearchAddress().getUnit() : LengthUnitEnum.KiloMeter;

                Double distanceRequiredInKm = worker.getJobSearchAddress().getMaxJobDistance()
                        * distanceUnit.getConversionRateToKm();
                result = distanceRequiredInKm >= distance.orElse(0.0);
            }
        } else {
            result = true; // for any other case,
        }

        LOGGER.debug(String.format("ProximityMatcher %s, %s : %s "
                , worker.getUserId()
                , job.getJobId()
                , result ? "MATCHED" : "NOT MATCHED"));

        return result;
    }

    public Double getMaxDefaultDistanceKm() {
        return maxDefaultDistanceKm;
    }

    public void setMaxDefaultDistanceKm(Double maxDefaultDistanceKm) {
        this.maxDefaultDistanceKm = maxDefaultDistanceKm;
    }

    protected Optional<Double> calculateDistance(GeoPoint point1, GeoPoint point2 ) {
        if (point1 == null || point2 == null || !point1.isGeoPointSet() || !point2.isGeoPointSet())
            return Optional.empty();
        return Optional.of (6371
                * Math.acos(Math.cos(Math.toRadians(point1.getLatitude())) * Math.cos(Math.toRadians(point2.getLatitude()))
                * Math.cos(Math.toRadians(point2.getLongitude()) - Math.toRadians(point1.getLongitude()))
                + Math.sin(Math.toRadians(point1.getLatitude())) * Math.sin(Math.toRadians(point2.getLatitude())))
        );
    }
}
