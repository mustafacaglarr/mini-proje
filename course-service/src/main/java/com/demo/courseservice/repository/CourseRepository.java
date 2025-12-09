package com.demo.courseservice.repository;
import java.util.*; import com.demo.courseservice.model.Course;
public class CourseRepository {
 private Map<String,Course> data=new HashMap<>();
 public CourseRepository(){
  data.put("101",new Course("101","Java Bootcamp","Temel Java","inst1",199));
  data.put("102",new Course("102","Spring Microservices","Microservis temel akış","inst2",249));
  data.put("103",new Course("103","Clean Code","Kod kalitesi ve pratikler","inst1",149));
 }
 public List<Course> findAll(){ return new ArrayList<>(data.values()); }
 public Course findById(String id){ return data.get(id); }
}
