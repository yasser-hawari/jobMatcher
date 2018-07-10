package com.sj.jobMatcher.service.impl;

import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Worker;
import com.sj.jobMatcher.service.WorkerNotFoundException;
import com.sj.jobMatcher.service.DataIsNotReadyException;
import com.sj.jobMatcher.service.JobMatcherRegistery;
import com.sj.jobMatcher.service.Matcher;
import com.sj.jobMatcher.service.ProviderDataCacheService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class MatchingServiceImplTest {


    MatchingServiceImpl matchingService;

    @MockBean
    JobMatcherRegistery jobMatcherRegistery;

    @MockBean
    ProviderDataCacheService providerDataCacheService;


    @Before
    public void  setup() throws WorkerNotFoundException, DataIsNotReadyException {

        Mockito.doNothing().when(jobMatcherRegistery).initializeMatchers();

        List<Job> jobsList = Arrays.asList(new Job(), new Job(), new Job(), new Job(), new Job());

        Map<Long, Worker> workersMapCache = new HashMap<>();
        workersMapCache.put(1L, new Worker());
        workersMapCache.put(2L, new Worker());
        workersMapCache.put(3L, new Worker());

        Mockito.when(providerDataCacheService.getJobsCache()).thenReturn(jobsList);
        Mockito.when(providerDataCacheService.getWorkersCache()).thenReturn(workersMapCache);

        matchingService = new MatchingServiceImpl(jobMatcherRegistery, providerDataCacheService);
    }

    @Test
    public void matchJobsWithOneMatcherFalse() throws WorkerNotFoundException, DataIsNotReadyException {

        Matcher matcher1 = Mockito.mock(Matcher.class);
        Matcher matcher2 = Mockito.mock(Matcher.class);
        Matcher matcher3 = Mockito.mock(Matcher.class);

        Mockito.when(matcher1.matchJob(any(Worker.class), any(Job.class))).thenReturn(true) ;
        Mockito.when(matcher1.isEnabled()).thenReturn(true) ;

        Mockito.when(matcher2.matchJob(any(Worker.class), any(Job.class))).thenReturn(false) ;
        Mockito.when(matcher2.isEnabled()).thenReturn(true) ;

        Mockito.when(matcher3.matchJob(any(Worker.class), any(Job.class))).thenReturn(true) ;
        Mockito.when(matcher3.isEnabled()).thenReturn(true) ;

        Map<String, Matcher> matchers = new HashMap<>(3);
        matchers.put("matcher1", matcher1);
        matchers.put("matcher2", matcher2);
        matchers.put("matcher3", matcher3);

        Mockito.when(jobMatcherRegistery.getMatchers()).thenReturn(matchers);


        List<Job> jobs = matchingService.matchJobs(1L, 3);

        // none shall pass !
        Assert.assertNotEquals(jobs.size(), 3);
    }


    @Test
    public void matchJobsWithAllMatchersTrue() throws WorkerNotFoundException, DataIsNotReadyException {

        Matcher matcher1 = Mockito.mock(Matcher.class);
        Matcher matcher2 = Mockito.mock(Matcher.class);
        Matcher matcher3 = Mockito.mock(Matcher.class);

        Mockito.when(matcher1.matchJob(any(Worker.class), any(Job.class))).thenReturn(true) ;
        Mockito.when(matcher1.isEnabled()).thenReturn(true) ;

        Mockito.when(matcher2.matchJob(any(Worker.class), any(Job.class))).thenReturn(true) ;
        Mockito.when(matcher2.isEnabled()).thenReturn(true) ;

        Mockito.when(matcher3.matchJob(any(Worker.class), any(Job.class))).thenReturn(true) ;
        Mockito.when(matcher3.isEnabled()).thenReturn(true) ;

        Map<String, Matcher> matchers = new HashMap<>(3);
        matchers.put("matcher1", matcher1);
        matchers.put("matcher2", matcher2);
        matchers.put("matcher3", matcher3);

        Mockito.when(jobMatcherRegistery.getMatchers()).thenReturn(matchers);

        List<Job> jobs = matchingService.matchJobs(1L, 3);

        // all three shall pass
        Assert.assertEquals(jobs.size(), 3);
    }


}