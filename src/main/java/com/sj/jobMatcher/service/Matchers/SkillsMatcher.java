package com.sj.jobMatcher.service.Matchers;

import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Worker;
import com.sj.jobMatcher.service.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class SkillsMatcher implements Matcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkillsMatcher.class);

    @Override
    public boolean matchJob(Worker worker, Job job) {
        boolean result = worker.getSkills()
                            .stream()
                            .filter(Objects::nonNull)
                            .filter(StringUtils::hasText)
                            .anyMatch(s -> s.equalsIgnoreCase(job.getJobTitle()));

        LOGGER.debug(String.format("SkillsMatching %s, %s : %s"
                , worker.getUserId()
                , job.getJobId()
                , result ? "MATCHED" : "NOT MATCHED"));

        return result;
    }

}
