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

import static org.junit.Assert.*;


public class SkillsMatchingTest {


    private static final Logger LOGGER = LoggerFactory.getLogger(SkillsMatchingTest.class);

    SkillsMatcher skillsMatcher = new SkillsMatcher();

    @Test
    public void matchJob() throws IOException {
        Set<String> requiredSkill = Stream.of("Director of First Impressions", "Sous chef", "VP of Misc. Stuff" )
                                        .collect(Collectors.toSet());

        Worker worker = MatchersTestingUtility.getWorker2();
        Job job = MatchersTestingUtility.getJob2();

        worker.setSkills(requiredSkill);
        job.setJobTitle("Sous chef");

        assertTrue(skillsMatcher.matchJob(worker, job));

        job.setJobTitle("ABCD");

        assertFalse(skillsMatcher.matchJob(worker, job));

    }
}