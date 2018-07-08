package com.sj.jobMatcher.rest;

public class WorkerNotFoundException extends Exception {

    private Long workerId;

    public WorkerNotFoundException(Long workerId) {
        this.workerId = workerId;
    }

    public Long getWorkerId() {
        return workerId;
    }
}