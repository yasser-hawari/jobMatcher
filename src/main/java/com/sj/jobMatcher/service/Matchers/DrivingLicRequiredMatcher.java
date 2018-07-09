package com.sj.jobMatcher.service.Matchers;

import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Worker;
import com.sj.jobMatcher.service.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DrivingLicRequiredMatcher implements Matcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(DrivingLicRequiredMatcher.class);

    @Override
    public boolean matchJob(final Worker worker, final Job job) {
        boolean result = false;

        if(worker.getHasDriversLicense())
            result = true;
        else
            result = !job.getDriverLicenseRequired() ? true : worker.getHasDriversLicense();

        LOGGER.debug(String.format("DrivingLicRequiredMatcher %s, %s : %s"
                , worker.getUserId()
                , job.getJobId()
                , result ? "MATCHED" : "NOT MATCHED"));

        return result;
    }

}
