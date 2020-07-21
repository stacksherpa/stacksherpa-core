package com.stacksherpa;

import lombok.Builder;

@Builder
public class InvocationUpdateStatusCommand {

  public final String id;

  public final String status;

}
