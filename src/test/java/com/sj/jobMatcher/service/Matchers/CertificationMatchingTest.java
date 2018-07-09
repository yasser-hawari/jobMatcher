package com.sj.jobMatcher.service.Matchers;

import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Worker;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CertificationMatchingTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CertificationMatchingTest.class);

    CertificationMatching certificationMatching = new CertificationMatching();

    @Test
    public void matchJob() throws IOException {

        Set<String> workerCerts = Stream.of("Outside the Box Thinker", "The Human Handbook", null, "Excellence in Organization", "The Encouraging Word Award", "Marvelous Multitasker", "The Risk Taker", "Healthy Living Promoter", "Office Lunch Expert", "Outstanding Memory Award", "The Asker of Good Questions", "Excellence in Humor and Entertainment", "The Behind the Scenes Wonder", "Calm in the Eye of the Storm" )
                .collect(Collectors.toSet());


        Set<String> jobCerts = Stream.of( "Outstanding Memory Award", "The Encouraging Word Award", "Office Lunch Expert"  )
                .collect(Collectors.toSet());


        Worker worker = MatchersTestingUtility.getWorker2();
        Job job = MatchersTestingUtility.getJob2();

        worker.setCertificates(workerCerts);
        job.setRequiredCertificates(jobCerts);

        assertTrue(certificationMatching.matchJob(worker, job));

        jobCerts.clear();
        jobCerts.add("ABCD");

        job.setRequiredCertificates(jobCerts);

        assertFalse(certificationMatching.matchJob(worker, job));

        jobCerts.clear();

        assertTrue(certificationMatching.matchJob(worker, job));

    }
}