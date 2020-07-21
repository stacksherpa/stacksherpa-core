package com.stacksherpa;

import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.transaction.annotation.TransactionalEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class InvocationListener {

  private final ExecutionService executions;

  @TransactionalEventListener
  public void onCreated(InvocationCreatedEvent event) {
    log.info("invocation created {}", event.id);
    executions.execute(event.id);
  }

}
