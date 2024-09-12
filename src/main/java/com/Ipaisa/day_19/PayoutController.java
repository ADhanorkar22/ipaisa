//package com.Ipaisa.day_19;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/payout")
//public class PayoutController {
//
//    @Autowired
//    private PayoutService payoutService;
//
//    @PostMapping("/send")
//    public ResponseEntity<String> sendPayout(@RequestBody PayoutRequest request) {
//        try {
//            String response = payoutService.sendPayoutRequest(request);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//}