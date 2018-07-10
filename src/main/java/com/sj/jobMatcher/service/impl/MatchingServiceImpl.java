package com.sj.jobMatcher.service.impl;

import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Worker;
import com.sj.jobMatcher.service.WorkerNotFoundException;
import com.sj.jobMatcher.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MatchingServiceImpl implements MatchingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchingServiceImpl.class);


    private JobMatcherRegistery jobMatcherRegistery;

    private ProviderDataCacheService providerDataCacheService;


    public MatchingServiceImpl(JobMatcherRegistery jobMatcherRegistery
            , ProviderDataCacheService providerDataCacheService) {
        this.jobMatcherRegistery = jobMatcherRegistery;
        this.providerDataCacheService = providerDataCacheService;
    }

    @Override
    public List<Job> matchJobs(Long userId, int maxResults) throws WorkerNotFoundException, DataIsNotReadyException {
        LOGGER.debug(String.format("Matching jobs for worker [%s]", userId));
        if(userId == null)
            throw new IllegalArgumentException("workerId is required");

        Map<Long, Worker> workersCache = providerDataCacheService.getWorkersCache();
        List<Job> jobsCache = providerDataCacheService.getJobsCache();

        if(workersCache.isEmpty() || jobsCache.isEmpty()) {
            throw new DataIsNotReadyException(" Workers or Jobs Data is not ready.");
        }

        Worker worker = workersCache.get(userId);

        if(worker != null )
            return jobsCache.stream()
                    .filter(job -> match(worker, job))
//                    .peek(job -> LOGGER.debug(String.format(" Matching Job ID [%s] with Title '%s'", job.getJobId(), job.getJobTitle())))
                    .limit(maxResults)
                    .collect(Collectors.toList());
        else
            throw new WorkerNotFoundException(userId);
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

    @PostConstruct
    public void init(){
        providerDataCacheService.consumeWorkers();
        providerDataCacheService.consumeJobs();

    }

}







