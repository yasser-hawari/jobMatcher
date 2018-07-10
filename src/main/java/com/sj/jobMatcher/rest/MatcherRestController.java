package com.sj.jobMatcher.rest;


import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.service.DataIsNotReadyException;
import com.sj.jobMatcher.service.MatchingService;
import com.sj.jobMatcher.service.WorkerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/workers/",
        produces="application/json")
@CrossOrigin(origins="*")
public class MatcherRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatcherRestController.class);


    private int maxResults = 3;

    protected MatchingService matchingService;

    public MatcherRestController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @GetMapping("/{workerId:[\\d]+}/matchedJobs")
    public List<Job> matchJobs(@PathVariable(name = "workerId") Long workerId) throws WorkerNotFoundException, DataIsNotReadyException {
        return matchingService.matchJobs(workerId, maxResults);
    }

    // exception handlers

    @ExceptionHandler(WorkerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse WorkerNotFound(WorkerNotFoundException e) {
        Long workerId = e.getWorkerId();
        return new ErrorResponse(1000, "workerId [" + workerId + "] not found");
    }

    @ExceptionHandler(DataIsNotReadyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse dataIsNotReadyException(DataIsNotReadyException e) {
        LOGGER.debug("DataIsNotReadyException handler invoked. ", e);
        return new ErrorResponse(2000, "System data is not ready yet, please try again later.");
    }

    @Value( "${matcherRestController.maxResults.default}" )
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
}