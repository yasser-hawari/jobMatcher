package com.sj.jobMatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*

Swipejobs is all about matching Jobs to Workers.  With that in mind,
our challenge is for you to develop a simple matching engine that presents Workers with appropriate Jobs.

We will provide a REST API for this task – /workers, which provides the list of all available Workers and /jobs which
provides a list of all available Jobs.

Your task is to produce your own REST API matches that will take a workerId and return no
more than three appropriate jobs for that Workers.

You may use any technology stack that you’d like to solve this problem.  We are most interested in the quality of the matching, as well as the readability of your code.

http://test.swipejobs.com/api/workers
http://test.swipejobs.com/api/jobs


 */
@SpringBootApplication
public class JobMatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobMatcherApplication.class, args);
	}
}
