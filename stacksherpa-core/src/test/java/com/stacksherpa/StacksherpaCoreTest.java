package com.stacksherpa;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.annotation.MicronautTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

@Slf4j
@MicronautTest
public class StacksherpaCoreTest {

  @Inject
  EmbeddedApplication application;

  @Inject
  private InvocationController controller;


  @Test
  void testItWorks() {
    Assertions.assertTrue(application.isRunning());

    CompletableFuture.runAsync(() -> {
      int i = 0;
      while(i < 3) {
        controller.webhook();
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e.getMessage(), e);
        }
      }
    });

    do {
      log.info("--- execution ---");
      for (Invocation invocation : controller.list().body()) {
        log.info("{}:{}", invocation.getId(), invocation.getStatus());
      }
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e.getMessage(), e);
      }
    } while (true);


  }

}
