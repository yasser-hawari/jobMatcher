package com.sj.jobMatcher.service;

import java.util.Map;

public interface JobMatcherRegistery {

    Map<String, Matcher> getMatchers();

    void initializeMatchers();

}
