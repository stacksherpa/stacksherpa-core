package com.stacksherpa;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

@Data
@Entity
@EntityListeners(InvocationListener.class)
public class Invocation {

  @Id
  private String id;

  private String job;

  private String status;

}
