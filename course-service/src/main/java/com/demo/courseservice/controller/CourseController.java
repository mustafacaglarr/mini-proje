package com.demo.courseservice.controller;

import com.demo.courseservice.model.Course;
import com.demo.courseservice.repository.CourseRepository;
import com.demo.courseservice.repository.EnrollmentRepository;
import com.demo.courseservice.service.PaymentClient;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
  private final CourseRepository courseRepository = new CourseRepository();
  private final EnrollmentRepository enrollmentRepository = new EnrollmentRepository();
  private final PaymentClient paymentClient;

  public CourseController(PaymentClient paymentClient) {
    this.paymentClient = paymentClient;
  }

  @GetMapping
  public List<Course> list() {
    return courseRepository.findAll();
  }

  @PostMapping("/purchase")
  public Map<String, Object> purchase(@RequestBody Map<String, String> request) {
    String userId = request.get("userId");
    String courseId = request.get("courseId");
    Course course = courseRepository.findById(courseId);
    if (course == null) {
      return Map.of("status", "error", "message", "Course not found");
    }
    boolean paid = paymentClient.charge(userId, courseId, course.price);
    if (!paid) {
      return Map.of("status", "error", "message", "Payment failed");
    }
    enrollmentRepository.add(userId, courseId);
    return Map.of(
        "status", "success",
        "message", "Course purchased",
        "course", course,
        "purchasedCourses", enrolledCourses(userId)
    );
  }

  @GetMapping("/purchased/{userId}")
  public List<Course> purchased(@PathVariable String userId) {
    return enrolledCourses(userId);
  }

  private List<Course> enrolledCourses(String userId) {
    Set<String> ids = enrollmentRepository.findByUser(userId);
    List<Course> courses = new ArrayList<>();
    for (String id : ids) {
      Course c = courseRepository.findById(id);
      if (c != null) {
        courses.add(c);
      }
    }
    return courses;
  }
}
