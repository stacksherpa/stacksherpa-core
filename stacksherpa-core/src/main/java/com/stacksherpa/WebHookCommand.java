package com.stacksherpa;

import io.micronaut.http.server.cors.CorsFilter;
import lombok.Builder;

import java.beans.ConstructorProperties;

@Builder
public class WebHookCommand {

  public final String job;

  @ConstructorProperties({"job"})
  public WebHookCommand(String job) {
    this.job = job;
  }

}
