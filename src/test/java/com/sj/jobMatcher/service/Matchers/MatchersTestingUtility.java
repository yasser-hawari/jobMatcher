package com.sj.jobMatcher.service.Matchers;

import com.sj.jobMatcher.TestUtilities;
import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Worker;

import java.io.IOException;

public class MatchersTestingUtility {

    public static Worker getWorker1() throws IOException {
        return TestUtilities.toObjectFromClasspath("/com/sj/jobMatcher/service/Matchers/worker1.json", Worker.class);
    }

    public static Worker getWorker2() throws IOException{
        return TestUtilities.toObjectFromClasspath("/com/sj/jobMatcher/service/Matchers/worker2.json", Worker.class);
    }

    public static Job getJob1() throws IOException{
        return TestUtilities.toObjectFromClasspath("/com/sj/jobMatcher/service/Matchers/job1.json", Job.class);
    }

    public static Job getJob2() throws IOException{
        return TestUtilities.toObjectFromClasspath("/com/sj/jobMatcher/service/Matchers/job2.json", Job.class);
    }

//    @Test
//    public void test() throws IOException{
//        List<Worker> workers = TestUtilities.objectMapper.readValue( new URL("http://test.swipejobs.com/api/workers"),
//                new TypeReference<List<Worker>>(){});
//
//        List<Job> jobs = TestUtilities.objectMapper.readValue( new URL("http://test.swipejobs.com/api/jobs"),
//                new TypeReference<List<Job>>(){});
//
//        System.out.println(TestUtilities.toJson(jobs.get(13)));
//    }
}
