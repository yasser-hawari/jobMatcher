package com.sj.jobMatcher.service;

import com.sj.jobMatcher.model.Job;

import java.util.List;

public interface MatchingService {

    List<Job> matchJobs(Long userId, int maxResults) throws WorkerNotFoundException, DataIsNotReadyException;

}
