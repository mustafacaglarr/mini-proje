package com.demo.courseservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentClient {
  private final RestTemplate restTemplate;

  @Value("${payment.url:http://localhost:8083/payment/pay}")
  private String paymentUrl;

  public PaymentClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public boolean charge(String userId, String courseId, double amount) {
    Map<String, Object> body = new HashMap<>();
    body.put("userId", userId);
    body.put("courseId", courseId);
    body.put("amount", amount);
    ResponseEntity<Map> response = restTemplate.postForEntity(paymentUrl, body, Map.class);
    Object status = response.getBody() == null ? null : response.getBody().get("status");
    return status != null && "success".equalsIgnoreCase(status.toString());
  }
}
