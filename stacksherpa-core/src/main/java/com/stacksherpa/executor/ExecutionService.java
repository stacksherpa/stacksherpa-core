package com.stacksherpa.executor;

import com.stacksherpa.Invocation;
import com.stacksherpa.InvocationService;
import com.stacksherpa.InvocationStep;
import com.stacksherpa.InvocationUpdateStatusCommand;
import com.stacksherpa.Job;
import com.stacksherpa.JobService;
import com.stacksherpa.JobStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class ExecutionService {

  private final JobService jobs;

  private final InvocationService invocations;

  public void execute(String id) {
    Invocation invocation = invocations.show(id).orElseThrow(NoSuchElementException::new);
    Job job = jobs.show(invocation.getJob()).orElseThrow(NoSuchElementException::new);
    this.execute(job, invocation);
  }

  private void execute(Job job, Invocation invocation) {
    invocations.execute(InvocationUpdateStatusCommand.builder().id(invocation.getId()).status("running").build());
    try {
      for (InvocationStep invocationStep : invocation.getSteps()) {
        JobStep jobStep = job.getSteps().stream().filter(it -> it.getId().equals(invocationStep.getStep())).findFirst().orElseThrow(NoSuchElementException::new);
        log.info("+{}", jobStep.getCommands().get(0));
        invocations.execute(InvocationUpdateStatusCommand.builder().id(invocation.getId()).step(invocationStep.getId()).status("running").build());
        InvocationStepExecutor executor = null;
        if(Objects.isNull(jobStep.getImage())) {
          executor = new NoopInvocationStepExecutor();
        } else {
          executor = new DockerInvocationStepExecutor();
        }
        executor.execute(jobStep, invocationStep, log -> {
          invocations.execute(InvocationLogCreateCommand.builder().id(invocation.getId()).step(invocationStep.getId()).data(log).build());
        }).join();
        invocations.execute(InvocationUpdateStatusCommand.builder().id(invocation.getId()).step(invocationStep.getId()).status("success").build());
      }
      invocations.execute(InvocationUpdateStatusCommand.builder().id(invocation.getId()).status("success").build());
    } catch (Exception e) {
      e.printStackTrace();
      invocations.execute(InvocationUpdateStatusCommand.builder().id(invocation.getId()).status("fail").build());
    }
  }

}
