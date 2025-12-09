package com.demo.courseservice.repository;

import java.util.*;

public class EnrollmentRepository {
  private final Map<String, Set<String>> purchases = new HashMap<>();

  public void add(String userId, String courseId) {
    purchases.computeIfAbsent(userId, k -> new HashSet<>()).add(courseId);
  }

  public Set<String> findByUser(String userId) {
    return purchases.getOrDefault(userId, Collections.emptySet());
  }
}
