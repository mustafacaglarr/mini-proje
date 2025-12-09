package com.demo.paymentservice.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {
  @PostMapping("/pay")
  public Map<String, String> pay(@RequestBody Map<String, Object> r) {
    // Simulate always-successful payment with a transaction id.
    return Map.of(
        "status", "success",
        "transactionId", UUID.randomUUID().toString(),
        "message", "Payment OK"
    );
  }
}
