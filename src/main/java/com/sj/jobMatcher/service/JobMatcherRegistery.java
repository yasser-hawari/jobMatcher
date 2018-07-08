package com.sj.jobMatcher.service;

import java.util.Map;
import java.util.Set;

public interface JobMatcherRegistery {

    Map<String, Matcher> getMatchers();

    void initializeMatchers();

}
