package com.stacksherpa;

import com.stacksherpa.executor.InvocationLogCreateCommand;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.context.event.ApplicationEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class InvocationService {

  private final ApplicationEventPublisher events;

  private final InvocationRepository repository;

  private final JobService jobs;

  @NonNull
  public List<Invocation> list() {
    return repository.findAll();
  }

  public CompletableFuture<Invocation> execute(WebHookCommand command) {
    return CompletableFuture.supplyAsync(() -> {
      Job job = jobs.show(command.job).orElseThrow(NoSuchElementException::new);
      Invocation invocation = new Invocation();
      invocation.setId(UUID.randomUUID().toString());
      invocation.setJob(job.getId());
      invocation.setStatus("queued");
      for(JobStep step : job.getSteps()) {
        InvocationStep invocationStep = new InvocationStep();
        invocationStep.setId(UUID.randomUUID().toString());
        invocationStep.setStep(step.getId());
        invocationStep.setStatus("queued");
        invocation.getSteps().add(invocationStep);
      }
      invocation = repository.save(invocation);
      events.publishEvent(InvocationCreatedEvent.builder().id(invocation.getId()).build());
      return invocation;
    });
  }

  @NonNull
  public Optional<Invocation> show(@NonNull @NotNull String id) {
    return repository.findById(id);
  }

//  @Transactional
  public void execute(InvocationUpdateStatusCommand command) {
    Invocation invocation = repository.findById(command.id).orElseThrow(NoSuchElementException::new);
    if(Objects.isNull(command.step)) {
      invocation.setStatus(command.status);
    } else {
      InvocationStep step = invocation.getSteps().stream().filter(it -> it.getId().equals(command.step)).findFirst().orElseThrow(NoSuchElementException::new);
      step.setStatus(command.status);
    }
    repository.update(invocation);
  }

  public void execute(InvocationLogCreateCommand command) {
    Invocation invocation = repository.findById(command.id).orElseThrow(NoSuchElementException::new);
    InvocationStep step = invocation.getSteps().stream().filter(it -> it.getId().equals(command.step)).findFirst().orElseThrow(NoSuchElementException::new);
    step.getLogs().add(command.data);
    repository.update(invocation);
  }

  public void delete(@NonNull @NotNull String id) {
    repository.deleteById(id);
  }



}
