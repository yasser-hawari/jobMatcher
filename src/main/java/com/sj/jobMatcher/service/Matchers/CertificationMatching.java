package com.sj.jobMatcher.service.Matchers;

import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Worker;
import com.sj.jobMatcher.service.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CertificationMatching implements Matcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(CertificationMatching.class);

    @Override
    public boolean matchJob(final Worker worker, final Job job) {

        boolean result = job.getRequiredCertificates().stream()
                    .filter(Objects::nonNull)
                    .filter(StringUtils::hasText)
                    .allMatch(cert ->
                            worker.getCertificates().contains(cert));

        LOGGER.debug(String.format("CertificationMatching %s, %s : %s"
                , worker.getUserId()
                , job.getJobId()
                , result ? "MATCHED" : "NOT MATCHED"));

        return result;
    }
}
