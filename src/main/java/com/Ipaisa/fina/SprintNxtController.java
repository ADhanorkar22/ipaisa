package com.Ipaisa.fina;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.Ipaisa.dto.RequestPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
@RestController
@RequestMapping("/auth/sprintnxt")
public class SprintNxtController {
    @Autowired
    private SprintNxtService sprintNxtService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    //    @PostMapping("/payout")
//    public ResponseEntity<?> performPayout(@RequestBody String payload) {
//        try {
//            String response = sprintNxtService.encryptAndSendData(payload);
////          System.out.println("\n\n\t Response ========>>>>>>"+response.toString());
////            return ResponseEntity.ok(response);
//            Object jsonResponse = objectMapper.readValue(response, Object.class);
//            return ResponseEntity.ok(jsonResponse);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
//        }
//    }
//}
    @PostMapping("/payout")
    public ResponseEntity<?> performPayout(@RequestBody Map<String, Object> payload) {
        Object Response;
        try {
            // Convert payload to JSON string
            String payloadJson = objectMapper.writeValueAsString(payload);
            // Encrypt and send data
            Response = sprintNxtService.encryptAndSendData(payloadJson);
            // Parse the response if needed
            //Object jsonResponse = objectMapper.readValue(response, Object.class);

            return ResponseEntity.ok(Response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/payoutv2")
    public ResponseEntity<String> makePayout(@RequestBody RequestPayload requestBody) {
        System.out.println("Request Body --->>>>> " + requestBody.getBody());
        System.out.println("Key --->>>>> " + requestBody.getKey());
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/hal+json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, requestBody.getBody());
        Request request = new Request.Builder()
                .url("https://uatnxtgen.sprintnxt.in/api/v1/payout/PAYOUT")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("partnerId", "NlRJUE5OUk")
                .addHeader("client-id", "U1BSX05YVF91YXRfOTc3YThmYmJiY2VmNjU4Nw==")
                .addHeader("key", requestBody.getKey())
                .addHeader("content-type", "application/hal+json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println(responseBody);
            if (!response.isSuccessful()) {
                return ResponseEntity.status(response.code()).body(responseBody);
            }
            return ResponseEntity.ok(responseBody);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }
}
