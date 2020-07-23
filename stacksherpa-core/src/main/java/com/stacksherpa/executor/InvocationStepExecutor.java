package com.stacksherpa.executor;

import com.stacksherpa.InvocationStep;
import com.stacksherpa.JobStep;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface InvocationStepExecutor {

  CompletableFuture<Void> execute(JobStep jobStep, InvocationStep invocationStep, Consumer<String> log);

}
