package com.demo.matchingservice.controller;

import com.demo.matchingservice.repository.InstructorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/match")
public class MatchingController {
  private final InstructorRepository repo = new InstructorRepository();

  @PostMapping("/request")
  public Map<String, Object> match(@RequestBody Map<String, String> r) {
    var inst = repo.getAvailable();
    Map<String, Object> response = new HashMap<>();
    response.put("requestId", UUID.randomUUID().toString());
    response.put("userId", r.get("userId"));
    response.put("topic", r.getOrDefault("topic", "live-session"));
    response.put("assignedInstructor", inst);
    response.put("notification", "Instructor " + inst + " notified");
    return response;
  }
}
