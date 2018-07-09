package com.sj.jobMatcher.service.Matchers;

import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Worker;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class DrivingLicRequiredMatcherTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrivingLicRequiredMatcher.class);

    DrivingLicRequiredMatcher drivingLicRequiredMatcher = new DrivingLicRequiredMatcher();

    @Test
    public void matchJob() throws IOException {
        Worker worker = MatchersTestingUtility.getWorker2();
        Job job = MatchersTestingUtility.getJob2();

        job.setDriverLicenseRequired(true);
        worker.setHasDriversLicense(true);

        assertTrue(drivingLicRequiredMatcher.matchJob(worker, job));

        job.setDriverLicenseRequired(true);
        worker.setHasDriversLicense(false);

        assertFalse(drivingLicRequiredMatcher.matchJob(worker, job));

        job.setDriverLicenseRequired(false);
        worker.setHasDriversLicense(false);

        assertTrue(drivingLicRequiredMatcher.matchJob(worker, job));

        job.setDriverLicenseRequired(false);
        worker.setHasDriversLicense(true);

        assertTrue(drivingLicRequiredMatcher.matchJob(worker, job));

    }
}