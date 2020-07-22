package com.stacksherpa;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface InvocationRepository extends JpaRepository<Invocation, String> {
}
