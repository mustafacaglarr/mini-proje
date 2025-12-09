package com.demo.matchingservice.repository;

import java.util.List;

public class InstructorRepository {
  private final List<String> list = List.of("inst1", "inst2", "inst3");

  public String getAvailable() {
    return list.get(0);
  }
}
