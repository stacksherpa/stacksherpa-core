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
    Assertions.assertTrue(application.getApplicationContext().isRunning());
    CompletableFuture.runAsync(() -> {
      int i = 0;
      while(i < 1) {
        controller.webhook(WebHookCommand.builder().job("job.01").build());
        i++;
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e.getMessage(), e);
        }
      }
    });

    do {
      CompletableFuture.runAsync(() -> {
        log.info("--- execution ---");
        for (Invocation invocation : controller.list().body()) {
          log.info("{}:{}", invocation.getId(), invocation.getStatus());
          for (InvocationStep step : invocation.getSteps()) {
            log.info("    {}:{}", step.getId(), step.getStatus());
            for (String data : step.getLogs()) {
              log.info("      {}",data);
            }
          }
        }
      });
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e.getMessage(), e);
      }
    } while (true);


  }

}
