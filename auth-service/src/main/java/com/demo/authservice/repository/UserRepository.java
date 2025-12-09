package com.demo.authservice.repository;
import java.util.*; import com.demo.authservice.model.User;
public class UserRepository {
 private Map<String,User> users=new HashMap<>();
 public UserRepository(){
  users.put("1",new User("1","user1","123","user"));
  users.put("2",new User("2","inst1","123","instructor"));
  users.put("3",new User("3","admin1","123","admin"));
 }
 public User findByUsername(String u){
  return users.values().stream().filter(x->x.username.equals(u)).findFirst().orElse(null);
 }
}
