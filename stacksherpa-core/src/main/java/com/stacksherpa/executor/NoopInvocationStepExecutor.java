package com.stacksherpa.executor;

import com.stacksherpa.InvocationStep;
import com.stacksherpa.JobStep;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
public class NoopInvocationStepExecutor implements InvocationStepExecutor {

  @Override
  public CompletableFuture<Void> execute(JobStep jobStep, InvocationStep invocationStep, Consumer<String> logs) {
    return CompletableFuture.runAsync(() -> {
      for (String command : jobStep.getCommands()) {
        logs.accept(command);
        sleep(Duration.ofSeconds(10));
      }
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
