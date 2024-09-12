//package com.Ipaisa.UserController;
//
//import com.Ipaisa.Service.DeeplinkService;
//import com.Ipaisa.dto.DeeplinkResponseDTO;
//import com.Ipaisa.entity.DeeplinkRequest;
//import com.Ipaisa.dto.DeeplinkResponseDTO;
//import com.Ipaisa.entity.DeeplinkRequest;
//import com.Ipaisa.Service.DeeplinkService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/deeplink")
//public class DeeplinkController {
//
// @Autowired
// private DeeplinkService deeplinkService;
//
// @PostMapping("/generate")
// public ResponseEntity<DeeplinkResponseDTO> generateDeeplink(@RequestBody DeeplinkRequest request) {
//     DeeplinkResponseDTO responseDTO = deeplinkService.generateDeeplink(request);
//     return new ResponseEntity<>(responseDTO, HttpStatus.OK);
// }
//}
