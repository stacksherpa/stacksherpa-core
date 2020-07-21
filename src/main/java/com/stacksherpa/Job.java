package com.stacksherpa;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Job {

  @Id
  private String id;

}
