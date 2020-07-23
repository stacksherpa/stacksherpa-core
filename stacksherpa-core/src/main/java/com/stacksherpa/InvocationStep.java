package com.stacksherpa;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class InvocationStep {

  @Id
  private String id;

  private String step;

  private String status;

  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> logs = new ArrayList<>();

}
