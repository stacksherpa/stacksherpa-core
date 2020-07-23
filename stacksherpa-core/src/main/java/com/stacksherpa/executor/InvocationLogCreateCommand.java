package com.stacksherpa.executor;

import lombok.Builder;

@Builder
public class InvocationLogCreateCommand {

  public final String id;

  public final String step;

  public final String data;

}
