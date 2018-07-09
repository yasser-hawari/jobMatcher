package com.sj.jobMatcher.service;

import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProviderDataCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderDataCacheService.class);

    protected static final String WORKERS_LIST_URL = "http://test.swipejobs.com/api/workers";
    protected static final String JOBS_LIST_URL = "http://test.swipejobs.com/api/jobs";

    private Map<Long, Worker> workersCache = Collections.EMPTY_MAP;

    private List<Job> jobsCache = Collections.EMPTY_LIST;


    public Map<Long, Worker> getWorkersCache(){
        return Collections.unmodifiableMap(workersCache);
    }

    public List<Job>  getJobsCache(){
        return Collections.unmodifiableList(jobsCache);
    }

    @Retryable(
            value = { Exception.class },
            maxAttempts = 10,
            backoff = @Backoff(delay = 5000, multiplier = 2.0))
    @Async
    public void consumeWorkers(){
        LOGGER.debug("Attempting to consume Workers from " + WORKERS_LIST_URL);
        RestTemplate restTemplate = new RestTemplate();
        try {

            RequestEntity request  = RequestEntity.get(new URI(WORKERS_LIST_URL))
                    .accept(MediaType.APPLICATION_JSON_UTF8).build();

            ResponseEntity<List<Worker>> responseEntity = restTemplate
                    .exchange(request, new ParameterizedTypeReference<List<Worker>>(){});

            if(responseEntity.getStatusCode() == HttpStatus.OK) {
                workersCache = responseEntity.getBody().stream()
                        .collect(Collectors.collectingAndThen(
                                Collectors.toMap(
                                        Worker::getUserId,
                                        w -> w,
                                        (w1, w2) -> w1)
                                , Collections::unmodifiableMap)
                        );
            } else {
                LOGGER.error(String.format("Failed to consume workers API. HTTP Response : %s \n BODY : %s "
                        , responseEntity.getStatusCode()
                        , responseEntity.getBody()));
            }


            LOGGER.debug("Caching a total of " + workersCache.size() + " Workers records. ");

        } catch (URISyntaxException e) {
            LOGGER.error("Failed to consume workers from " + WORKERS_LIST_URL + ". THIS WILL WILL NOT BE RETRIED. PLEASE FIX THE URL ", e);
        } catch (Exception e) {
            LOGGER.error("Failed to consume workers from " + WORKERS_LIST_URL + ". Retry mechanism in place.");
            throw e;
        }
    }

    @Retryable(
            value = { Exception.class },
            maxAttempts = 10,
            backoff = @Backoff(delay = 5000, multiplier = 2.0))
    @Async
    public void consumeJobs(){
        LOGGER.debug("Attempting to consume Workers from " + JOBS_LIST_URL);
        RestTemplate restTemplate = new RestTemplate();
        try {

            RequestEntity request  = RequestEntity.get(new URI(JOBS_LIST_URL))
                    .accept(MediaType.APPLICATION_JSON_UTF8).build();

            ResponseEntity<List<Job>> responseEntity = restTemplate
                    .exchange(request, new ParameterizedTypeReference<List<Job>>(){});

            if(responseEntity.getStatusCode() == HttpStatus.OK) {
                jobsCache = responseEntity.getBody().stream().collect(
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                Collections::unmodifiableList)
                );
            } else {
                LOGGER.error(String.format("Failed to consume Jobs API. HTTP Response : %s \n BODY : %s "
                        , responseEntity.getStatusCode()
                        , responseEntity.getBody()));
            }

            LOGGER.debug("Caching a total of " + jobsCache.size() + " Jobs records. ");
        } catch (URISyntaxException e) {
            LOGGER.error("Failed to consume jobs from " + JOBS_LIST_URL + ". THIS WILL WILL NOT BE RETRIED. PLEASE FIX THE URL ", e);
        } catch (Exception e) {
            LOGGER.error("Failed to consume jobs from " + JOBS_LIST_URL + ". Retry mechanism in place.");
            throw e;
        }
    }


}
