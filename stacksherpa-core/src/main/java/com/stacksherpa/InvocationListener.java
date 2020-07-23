package com.stacksherpa;

import com.stacksherpa.executor.ExecutionService;
import io.micronaut.runtime.event.annotation.EventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class InvocationListener {

  private final ExecutionService executions;

  @EventListener
  public void onCreated(InvocationCreatedEvent event) {
    executions.execute(event.id);
  }

}
