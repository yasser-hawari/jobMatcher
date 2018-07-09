package com.sj.jobMatcher.service.Matchers;

import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Location;
import com.sj.jobMatcher.model.Worker;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

public class ProximityMatcherTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProximityMatcherTest.class);

    ProximityMatcher proximityMatcher = new ProximityMatcher();

    @Test
    public void matchJob() throws IOException {
        Worker worker = MatchersTestingUtility.getWorker1();

        // worker in Sydney
        worker.getJobSearchAddress().setLatitude(33.8688);
        worker.getJobSearchAddress().setLongitude(151.2093);

        // job in Melbourn
        Job job = MatchersTestingUtility.getJob1();
        job.setLocation(new Location(37.8136, 144.9631));

        assertFalse("Worker in Sydney was wrongly matched to a job in Melbourn", proximityMatcher.matchJob(worker, job));

        // job nearby in sydney
        job.setLocation(new Location( 33.8688, 151.0));
        assertFalse("Worker was not matched to a job near Sydney", proximityMatcher.matchJob(worker, job));
    }

    @Test
    public void matchJob2() throws IOException {
        Worker worker = MatchersTestingUtility.getWorker1();

        // worker in sydney
        worker.getJobSearchAddress().setLatitude(33.8688);
        worker.getJobSearchAddress().setLongitude(151.2093);

        // job in melbourn
        Job job = MatchersTestingUtility.getJob1();
        job.setLocation(new Location(33.8688, 151.0));

        Optional<Double> distance = proximityMatcher.calculateDistance(
                new Location(33.8688, 151.2093),    //  point 1 - Sydney
                new Location(33.8688, 151.0)     // less than 20 km from point 1
        );

        worker.getJobSearchAddress().setMaxJobDistance(distance.get()+1.0);

        assertTrue("Worker in Sydney failed to match to job nearby in Sydney", proximityMatcher.matchJob(worker, job));
    }

    /**
     * smoke test the distance calculation
     */
    @Test
    public void calculateDistanceSmokeTest() {

        Optional<Double> distance = proximityMatcher.calculateDistance(
                new Location(33.8688, 151.2093),    // Sydney
                new Location(37.8136, 144.9631)     // Melbourne
                );

        assertEquals(713.4274807201274d, 713.42, 0.01d );    }

}