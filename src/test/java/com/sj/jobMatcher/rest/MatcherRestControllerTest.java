package com.sj.jobMatcher.rest;


import com.fasterxml.jackson.core.type.TypeReference;
import com.sj.jobMatcher.TestUtilities;
import com.sj.jobMatcher.model.Job;
import com.sj.jobMatcher.service.DataIsNotReadyException;
import com.sj.jobMatcher.service.impl.MatchingServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MatcherRestController.class)
public class MatcherRestControllerTest {

    @MockBean
    MatchingServiceImpl matchingService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void matchJobsWithThreeMatches() throws Exception {

        List<Job> jobs = TestUtilities.toObjectFromClasspath("/com/sj/jobMatcher/rest/jobs.json",
                                                new TypeReference<List<Job>>(){});

        Mockito.when(matchingService.matchJobs(1L, 3)).thenReturn(jobs);

        mvc.perform(get("/api/workers/1/matchedJobs")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void matchJobsWithZeroMatch() throws Exception {

        Mockito.when(matchingService.matchJobs(1L, 3)).thenReturn(new ArrayList<>());

        mvc.perform(get("/api/workers/1/matchedJobs")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void matchJobsWithServiceNotReady() throws Exception {

        Mockito.when(matchingService.matchJobs(1L,3 )).thenThrow(new DataIsNotReadyException(""));

        mvc.perform(get("/api/workers/1/matchedJobs")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError()) ;

    }
}



