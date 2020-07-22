package com.stacksherpa;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.context.event.ApplicationEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

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

  @Transactional
  public Invocation execute(String id) {
    Job job = jobs.show(id).orElseThrow(NoSuchElementException::new);
    log.info("create invocation");
    Invocation invocation = new Invocation();
    invocation.setId(UUID.randomUUID().toString());
    invocation.setJob(job.getId());
    invocation.setStatus("queued");
    invocation = repository.save(invocation);
    events.publishEvent(InvocationCreatedEvent.builder().id(invocation.getId()).build());
    return invocation;
  }

  @NonNull
  public Optional<Invocation> show(@NonNull @NotNull String id) {
    return repository.findById(id);
  }

  public Invocation execute(InvocationUpdateStatusCommand command) {
    Invocation invocation = repository.findById(command.id).orElseThrow(NoSuchElementException::new);
    invocation.setStatus(command.status);
    return repository.update(invocation);
  }

  public void delete(@NonNull @NotNull String id) {
    repository.deleteById(id);
  }

}
