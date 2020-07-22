package com.stacksherpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class ExecutionService {

  private final EntityManager em;

  private final InvocationService invocations;

  public void execute(String id) {
    CompletableFuture.runAsync(() -> {
      log.info("executing invocation {}", id);
      invocations.execute(InvocationUpdateStatusCommand.builder().id(id).status("running").build());
      sleep(Duration.ofSeconds(15));
      invocations.execute(InvocationUpdateStatusCommand.builder().id(id).status("success").build());
    });
  }

  private void sleep(Duration duration) {
    try {
      Thread.sleep(duration.toMillis());
    } catch (InterruptedException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

}
