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
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Controller("/invocations")
@RequiredArgsConstructor
public class InvocationController {

  private final InvocationService invocations;

  @Get
  public HttpResponse<List<Invocation>> list() {
    return HttpResponse.ok(invocations.list());
  }

  @Post
  public HttpResponse<Void> webhook(@Body WebHookCommand command) {
    CompletableFuture<Invocation> invocation = invocations.execute(command);
    return HttpResponse.accepted();
  }

  @Delete("/{id}")
  public HttpResponse<Void> delete(@PathVariable("id") String id) {
    invocations.delete(id);
    return HttpResponse.accepted();
  }

  @Get("/{id}/steps/{step}/logs")
  public HttpResponse<List<String>> logs(@PathVariable("id") String id, @PathVariable("step") String step) {
    Invocation invocation = invocations.show(id).orElseThrow(NoSuchElementException::new);
    InvocationStep invocationStep = invocation.getSteps().stream().filter(it -> it.getId().equals(step)).findFirst().orElseThrow(NoSuchElementException::new);
    return HttpResponse.ok(invocationStep.getLogs());
  }

  @Post("/test")
  public HttpResponse<Void> test(@Body String payload) {
    log.info(payload);
    return HttpResponse.ok();
  }

}
