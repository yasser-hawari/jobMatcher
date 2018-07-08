package com.sj.jobMatcher.service.Matchers;

import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Worker;
import com.sj.jobMatcher.service.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CertificationMatching implements Matcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(CertificationMatching.class);

    @Override
    public boolean matchJob(final Worker worker, final Job job) {

        boolean result = worker.getCertificates().containsAll(job.getRequiredCertificates());

        LOGGER.debug(String.format("CertificationMatching %s, %s : %s"
                , worker.getUserId()
                , job.getJobId()
                , result ? "MATCHED" : "NOT MATCHED"));

        return result;
    }
}
