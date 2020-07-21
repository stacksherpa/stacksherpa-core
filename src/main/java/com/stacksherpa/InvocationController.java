package com.stacksherpa;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class InvocationController {

  private final InvocationService invocations;

  @Get
  public HttpResponse<List<Invocation>> list() {
    return HttpResponse.ok(invocations.list());
  }

  @Post
  public HttpResponse<Void> webhook() {
    log.info("webhook received");
    invocations.execute("job.01");
    return HttpResponse.accepted();
  }

}
