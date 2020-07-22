package com.stacksherpa;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;

@Slf4j
public class Application implements ApplicationEventListener<ServerStartupEvent> {

  @Inject
  private JobService jobs;

  @Override
  public void onApplicationEvent(ServerStartupEvent event) {
    Job job = new Job();
    job.setId("job.01");
    jobs.save(job);
  }

  public static void main(String[] args) {
    Micronaut.run(Application.class);
  }

}
