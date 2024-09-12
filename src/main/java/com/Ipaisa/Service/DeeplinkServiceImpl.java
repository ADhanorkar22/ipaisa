//package com.Ipaisa.Service;
//
//import com.Ipaisa.Repository.UserRepositoryy;
//import com.Ipaisa.dto.DeeplinkResponseDTO;
//import com.Ipaisa.entity.DeeplinkRequest;
//import com.Ipaisa.entity.Userss;
//import com.Ipaisa.dto.DeeplinkResponseDTO;
//import com.Ipaisa.entity.DeeplinkRequest;
//import com.Ipaisa.entity.Userss;
//import com.Ipaisa.Repository.UserRepositoryy;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.Base64;
//
//@Service
//public class DeeplinkServiceImpl implements DeeplinkService {
//
//    @Value("${your.service.charge}")
//    private double serviceCharge;
//
//    @Autowired
//    private UserRepositoryy userRepository;
//
//    @Override
//    public DeeplinkResponseDTO generateDeeplink(DeeplinkRequest request) {
//        Userss user = userRepository.findByUserNameAndUserMobileNo(request.getUserName(), request.getUserMobileNo());
//        String actionKeyword = (user != null) ? "payin" : "register";
//        double totalAmount = request.getAmount() * (1 + serviceCharge / 100);
//
//        String encryptedDeeplink = generateEncryptedDeeplink(request, totalAmount, actionKeyword);
//
//        DeeplinkResponseDTO responseDTO = new DeeplinkResponseDTO();
//        responseDTO.setEncryptedDeeplink(encryptedDeeplink);
//        return responseDTO;
//    }
//
//    private String generateEncryptedDeeplink(DeeplinkRequest request, double totalAmount, String actionKeyword) {
//        String deeplink = String.format("%s?amount=%.2f&userMobileNo=%s&retailerName=%s&retailerMobileNo=%s&userName=%s",
//                actionKeyword, totalAmount, request.getUserMobileNo(), request.getRetailerName(),
//                request.getRetailerMobileNo(), request.getUserName());
//
//        String encryptedDeeplink = "myreactnativeapp://" + encrypt(deeplink);
//
//        return encryptedDeeplink;
//    }
//
//    private String encrypt(String plainText) {
//        return Base64.getEncoder().encodeToString(plainText.getBytes());
//    }
//}
