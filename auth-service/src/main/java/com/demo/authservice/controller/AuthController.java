package com.demo.authservice.controller;
import org.springframework.web.bind.annotation.*; import java.util.*;
import com.demo.authservice.repository.*;
@RestController @RequestMapping("/auth")
public class AuthController {
 private UserRepository repo=new UserRepository();
 @PostMapping("/login")
 public Map<String,String> login(@RequestBody Map<String,String> r){
  var u=repo.findByUsername(r.get("username"));
  if(u==null||!u.password.equals(r.get("password"))) return Map.of("error","Invalid");
  return Map.of("userId",u.id,"role",u.role,"token","mock-token");
 }
}
