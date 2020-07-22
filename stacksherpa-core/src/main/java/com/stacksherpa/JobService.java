package com.stacksherpa;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class JobService {

  private final JobRepository repository;

  @NonNull
  public List<Job> list() {
    return repository.findAll();
  }

  public Job save(Job job) {
    repository.save(job);
    log.info("job created");
    return job;
  }

  public Job save() {
    Job entity = new Job();
    entity.setId(UUID.randomUUID().toString());
    return this.save(entity);
  }

  @NonNull
  public Optional<Job> show(@NonNull @NotNull String id) {
    return repository.findById(id);
  }

  @NonNull
  public <S extends Job> S update(@NonNull @Valid @NotNull S entity) {
    return repository.update(entity);
  }

  public void delete(@NonNull @NotNull String id) {
    repository.deleteById(id);
  }

}
