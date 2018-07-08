package com.sj.jobMatcher.rest;


import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.service.MatchingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/workers/",
        produces="application/json")
//@CrossOrigin(origins="*")
public class MatcherRestController {

    protected MatchingService matchingService;

    public MatcherRestController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @GetMapping("/{workerId:[\\d]+}/matchedJobs")
    public List<Job> matchJobs(@PathVariable(name = "workerId") Long workerId) throws WorkerNotFoundException {
        return matchingService.matchJobs(workerId);
    }

    @ExceptionHandler(WorkerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse WorkerNotFound(WorkerNotFoundException e) {
        Long workerId = e.getWorkerId();
        return new ErrorResponse(1000, "workerId [" + workerId + "] not found");
    }

}