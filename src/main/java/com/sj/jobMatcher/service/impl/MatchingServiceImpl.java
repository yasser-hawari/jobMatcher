package com.sj.jobMatcher.service.impl;

import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Worker;
import com.sj.jobMatcher.rest.WorkerNotFoundException;
import com.sj.jobMatcher.service.JobMatcherRegistery;
import com.sj.jobMatcher.service.Matcher;
import com.sj.jobMatcher.service.MatchingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MatchingServiceImpl implements MatchingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchingServiceImpl.class);

    protected static final String WORKERS_LIST_URL = "http://test.swipejobs.com/api/workers";
    protected static final String JOBS_LIST_URL = "http://test.swipejobs.com/api/jobs";
    protected static final int MAX_RESULTS = 3;

    private JobMatcherRegistery jobMatcherRegistery;

    private Map<Long, Worker> workersCache = Collections.EMPTY_MAP;

    private List<Job> jobsCache = Collections.EMPTY_LIST;


    public MatchingServiceImpl(JobMatcherRegistery jobMatcherRegistery) {
        this.jobMatcherRegistery = jobMatcherRegistery;
    }

    @Override
    public List<Job> matchJobs(Long userId) throws WorkerNotFoundException {

        if(userId == null)
            throw new IllegalArgumentException("workerId is required");

        Worker worker = workersCache.get(userId);

        if(worker != null )
            return matchJobs(worker, MAX_RESULTS);
        else
            throw new WorkerNotFoundException(userId);

    }

    private List<Job> matchJobs(final Worker worker, int maxResults) {
        LOGGER.debug(String.format("Matching jobs for worker %s", worker.getUserId()));
        return jobsCache.stream()
                .filter(job -> match(worker, job))
                .peek(job -> LOGGER.debug(String.format(" Matching Job ID [%s] with Title '%s'", job.getJobId(), job.getJobTitle())))
                .limit(maxResults)
                .collect(Collectors.toList());
    }

    /**
     * Simplified matching algorithm
     */
    protected Boolean match(final Worker worker,final Job job){
        return jobMatcherRegistery.getMatchers().values()
                            .stream()
                            .filter(Matcher::isEnabled)
                            .sorted(Comparator.comparing(Matcher::getPriority))
                            .allMatch( matcher -> matcher.matchJob(worker, job)) ;
    }

    public void consumeWorks(){
        RestTemplate restTemplate = new RestTemplate();
        try {

            RequestEntity request  = RequestEntity.get(new URI(WORKERS_LIST_URL))
                        .accept(MediaType.APPLICATION_JSON_UTF8).build();

            ResponseEntity<List<Worker>> responseEntity = restTemplate
                    .exchange(request, new ParameterizedTypeReference<List<Worker>>(){});

            System.out.println("size " + responseEntity.getBody().size());

            workersCache = responseEntity.getBody().stream()
                       .collect(Collectors.collectingAndThen(
                                   Collectors.toMap(
                                           Worker::getUserId,
                                           w -> w,
                                           (w1, w2) -> w1)
                                   , Collections::unmodifiableMap)
                       );

            System.out.println("size " + responseEntity.getBody().size());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void consumeJobs(){
        RestTemplate restTemplate = new RestTemplate();
        try {

            RequestEntity request  = RequestEntity.get(new URI(JOBS_LIST_URL))
                    .accept(MediaType.APPLICATION_JSON_UTF8).build();

            ResponseEntity<List<Job>> responseEntity = restTemplate
                    .exchange(request, new ParameterizedTypeReference<List<Job>>(){});

            jobsCache = responseEntity.getBody().stream().collect(
                        Collectors.collectingAndThen(
                        Collectors.toList(),
                        Collections::unmodifiableList)
                    );

            System.out.println("size " + responseEntity.getBody().size());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init(){
        consumeWorks();
        consumeJobs();
    }


}







