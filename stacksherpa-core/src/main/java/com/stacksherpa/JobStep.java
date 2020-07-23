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
public class JobStep {

  @Id
  private String id;

  private String image;

  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> commands = new ArrayList<>();

}
