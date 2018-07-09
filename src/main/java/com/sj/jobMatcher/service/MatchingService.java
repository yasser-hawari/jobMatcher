package com.sj.jobMatcher.service;

import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.rest.WorkerNotFoundException;

import java.util.List;

public interface MatchingService {

    List<Job> matchJobs(Long userId) throws WorkerNotFoundException, DataIsNotReadyException;

}
