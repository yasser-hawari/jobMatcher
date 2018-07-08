package com.sj.jobMatcher.service;

import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.model.Worker;

public interface Matcher {

    /**
     * the lower the value, the higher the priority. Low priority Matchers are executed first. More complex matchers
     * should have a lower priority.
     */
    default Integer getPriority(){ return 10; }

    boolean matchJob(Worker worker,Job job);

    default boolean isEnabled() {
        return true;
    };

}
