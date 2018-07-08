package com.sj.jobMatcher.service.Matchers;

import com.sj.jobMatcher.model.Availability;
import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Worker;
import com.sj.jobMatcher.service.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityMatcher implements Matcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvailabilityMatcher.class);

    @Override
    public boolean matchJob(final Worker worker, final Job job) {
        boolean result = false;
        if(worker.getAvailability() == null || worker.getAvailability().isEmpty())  // assumption
            result = true;
        else {
            final Integer jobStartDayIndex = job.getStartDate().getDayOfWeek().getValue();
            result = worker.getAvailability().stream()
                    .map(Availability::getDayIndex)
                    .anyMatch(index -> index.equals(jobStartDayIndex));
        }

        LOGGER.debug(String.format("AvailabilityMatcher %s, %s : %s"
                , worker.getUserId()
                , job.getJobId()
                , result ? "MATCHED" : "NOT MATCHED"));

        return result;
    }
}
