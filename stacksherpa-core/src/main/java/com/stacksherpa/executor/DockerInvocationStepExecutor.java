package com.stacksherpa.executor;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.WaitResponse;
import com.stacksherpa.DockerClients;
import com.stacksherpa.InvocationStep;
import com.stacksherpa.JobStep;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
public class DockerInvocationStepExecutor implements InvocationStepExecutor {

  private DockerClient docker = DockerClients.create();

  @Override
  public CompletableFuture<Void> execute(JobStep jobStep, InvocationStep invocationStep, Consumer<String> logs) {

    CompletableFuture<Void> future = new CompletableFuture<>();

    CreateContainerCmd createContainerCmd = docker.createContainerCmd(jobStep.getImage());
    createContainerCmd.withCmd("/bin/ash","-vc", jobStep.getCommands().stream().collect(Collectors.joining("\n")));
    CreateContainerResponse createContainerResponse = createContainerCmd.exec();

    log.info("docker container : {}", createContainerResponse.getId());

    docker.startContainerCmd(createContainerResponse.getId()).exec();
    docker.logContainerCmd(createContainerResponse.getId()).withStdOut(true).withStdErr(true).withFollowStream(true).withTailAll().exec(new ResultCallback<Frame>() {
      @Override
      public void onStart(Closeable closeable) {

      }

      @Override
      public void onNext(Frame object) {
        logs.accept(new String(object.getPayload()));
      }

      @Override
      public void onError(Throwable throwable) {

      }

      @Override
      public void onComplete() {

      }

      @Override
      public void close() throws IOException {

      }
    });
    docker.waitContainerCmd(createContainerResponse.getId()).exec(new ResultCallback<WaitResponse>() {
      @Override
      public void onStart(Closeable closeable) {

      }

      @Override
      public void onNext(WaitResponse object) {

      }

      @Override
      public void onError(Throwable throwable) {
        future.completeExceptionally(throwable);
      }

      @Override
      public void onComplete() {
        log.info("docker completed");
        future.complete(null);
      }

      @Override
      public void close() throws IOException {

      }
    });

    return future;

  }

}
