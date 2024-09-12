package com.Ipaisa.DeepLink;
//@CrossOrigin(origins = {"https://ipaisa.co.in", "https://www.ipaisa.co.in", "https://api.ipaisa.co.in", "http://localhost:3000","http://localhost:3001"})

import java.util.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ipaisa.CustomExceptions.KeyResponse;
import com.Ipaisa.Entitys.ApiResponse;
import com.Ipaisa.dto.DcryptResponse;
import com.Ipaisa.dto.DecryptPayloadWithKey;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/pg")
@CrossOrigin(origins = "*")
public class DeepLinkController {

	@Autowired
	private DeepLinkService deepLinkService;
	
	@Autowired
	private DataCompressionService dataCompressionService;
	
	@Autowired
	private EService eService;

	@Autowired
	private EncryptDecryptData encryptDecryptData;
	  
	
//	public ResponseEntity<?> GenrateKey(@RequestBody KeyGenrateDto dto) {
//		try {
//			
//		String Data=	deepLinkService.GenrateKey(dto);
//		return new  ResponseEntity(new KeyResponse(Data, true),HttpStatus.OK);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error redirecting to gateway");
//		}
//	}
	@PostMapping("/genrateKey")
	public ResponseEntity<?> generateKey(@RequestBody KeyGenrateDto dto) {
	    return encryptDecryptData.GenrateKey(dto);
	}
	
	@PostMapping(value = "/decryptPayload", produces = "application/json")
	public ResponseEntity<?> decryptPayload(@RequestBody DecryptPayloadWithKey obj) {
	    try {
	    	String Key="EDSOM";
	    	String Data=obj.getData().toString();
	    	System.out.println("------>> "+Data);
	        // Decrypt the data and convert it to a map
//	        Map<String, String> dataMap = deepLinkService.decrypt(obj,Key);
//	        Map<String, String> dataMap = deepLinkService.decrypt(obj,Key);
	    	Map<String, String> decryptedPayload = encryptDecryptData.decryptToJSON(Data);
	        System.out.println("PAYLOAD ___  __---- "+decryptedPayload);
	        
	        // Return the map as part of the response entity
	        return new ResponseEntity<>(new DcryptResponse("Success", "DecryptedResponse", decryptedPayload), HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiResponse("false", "Error processing decryption","Data Does Not Match"));
	    }
	}

//	
//	@GetMapping("/transaction/{mobileno}")
//	public ResponseEntity<?> getAllTxn(@PathVariable String mobileno)
//	{
//		List<DeeplinkPayin> list= deepLinkService.getAllTxn(mobileno);
//		if(list.isEmpty())
//		return new ResponseEntity(new ApiResponse("No Data Found ", false),HttpStatus.NO_CONTENT);
//		
//		return  ResponseEntity.ok(list);
//	}
	@GetMapping("/transaction/{txnid}")
	 public ResponseEntity<?> getAllTxn(@PathVariable String txnid) {
        // Call the service method to get the response
        return deepLinkService.getAllTransactionsByPhone(txnid);
    }
	
	
	 
}
