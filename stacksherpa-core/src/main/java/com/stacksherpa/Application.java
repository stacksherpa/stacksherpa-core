package com.stacksherpa;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.UUID;

@Slf4j
public class Application implements ApplicationEventListener<ServerStartupEvent> {

  @Inject
  private JobService jobs;

  @Override
  public void onApplicationEvent(ServerStartupEvent event) {
    Job job = new Job();
    job.setId("job.01");
    JobStep step = new JobStep();
    step.setId(UUID.randomUUID().toString());
//    step.setImage("woorea/base");
    step.getCommands().add("echo 'git clone'");
    step.getCommands().add("echo '...'");
    step.getCommands().add("echo '...'");
    job.getSteps().add(step);
    step = new JobStep();
    step.setId(UUID.randomUUID().toString());
//    step.setImage("woorea/base");
    step.getCommands().add("echo 'mvn clean install'");
    step.getCommands().add("echo '...'");
    step.getCommands().add("echo '...'");
    job.getSteps().add(step);
    jobs.save(job);
  }

  public static void main(String[] args) {
    Micronaut.run(Application.class);
  }

}
