package com.stacksherpa;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

public class DockerClients {

  public static final DockerClient create() {
    DockerClientConfig config = DefaultDockerClientConfig
      .createDefaultConfigBuilder()
      .build();

    DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
      .dockerHost(config.getDockerHost())
      .sslConfig(config.getSSLConfig())
      .build();

    return DockerClientImpl.getInstance(config, httpClient);
  }

}
