package com.sj.jobMatcher.service.impl;

import com.sj.jobMatcher.service.JobMatcherRegistery;
import com.sj.jobMatcher.service.Matcher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class JobMatcherRegisteryImpl implements JobMatcherRegistery, ApplicationContextAware {

    protected Map<String, Matcher> matchers = null;

    private ApplicationContext applicationContext;

    @Override
    public Map<String, Matcher> getMatchers() {
        if(matchers == null)
            initializeMatchers();

        return Collections.unmodifiableMap(matchers);
    }

    @Override
    public  void initializeMatchers() {
        synchronized (this){
            if(matchers == null)
                matchers = applicationContext.getBeansOfType(Matcher.class);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
