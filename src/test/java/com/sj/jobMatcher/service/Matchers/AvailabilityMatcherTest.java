package com.sj.jobMatcher.service.Matchers;

import com.sj.jobMatcher.model.Availability;
import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Worker;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class AvailabilityMatcherTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvailabilityMatcherTest.class);

    AvailabilityMatcher availabilityMatcher = new AvailabilityMatcher();

    @Test
    public void matchJob() throws IOException {
        Worker worker = MatchersTestingUtility.getWorker2();
        Job job = MatchersTestingUtility.getJob2();

        LocalDateTime dateTime = LocalDateTime.of(2018, 10, 10,12,30);  // Wednesday
        job.setStartDate(dateTime);

        // sample data worker is available on Wed
        assertTrue(availabilityMatcher.matchJob(worker, job));

        worker.getAvailability().clear();
        assertTrue(availabilityMatcher.matchJob(worker, job));      // matcher assumes empty availability is always true

        worker.getAvailability().add(new Availability(1, "Monday"));
        assertFalse(availabilityMatcher.matchJob(worker, job));
    }
}