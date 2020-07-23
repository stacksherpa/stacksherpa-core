package com.stacksherpa;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
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

  @Put("/{id}")
  public HttpResponse<Void> update(@PathVariable("id") String id, @Body Job job) {
    jobs.save(job);
    return HttpResponse.accepted();
  }

  @Delete("/{id}")
  public HttpResponse<Void> delete(@PathVariable("id") String id) {
    jobs.delete(id);
    return HttpResponse.accepted();
  }

}
