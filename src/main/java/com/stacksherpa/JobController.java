package com.stacksherpa;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Controller("/jobs")
@RequiredArgsConstructor
public class JobController {

  private final JobService jobs;

  @Get
  public HttpResponse<List<Job>> list() {
    return HttpResponse.ok(jobs.list());
  }

  @Post
  public HttpResponse<Void> save(@Body Job job) {
    jobs.save(job);
    return HttpResponse.accepted();
  }

}
