package com.Ipaisa.extrpayout;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ipaisa.Entitys.ApiResponse;
import com.Ipaisa.Entitys.InstantPayBody;
import com.Ipaisa.dto.DcryptResponse;
import com.Ipaisa.dto.DecryptPayloadWithKey;

@RestController
@RequestMapping("/ex")
public class ExtrPayoutController {

	@Autowired
	private ExtrPayoutService extrPayoutService;

	@PostMapping("/genratekey")
	public ResponseEntity<?> gerateKey(@RequestBody InstantPayBody payload) {

		return extrPayoutService.GenrateKey(payload);
	}

	@PostMapping("/decryptKey")
	public ResponseEntity<?> decreptKey(@RequestBody DecryptPayloadWithKey Key) {
		String Data = Key.getData();
		System.out.println(Data);
		Map<String, String> decryptedPayload;
		try {
			decryptedPayload = extrPayoutService.decryptToJSON(Key.getData());
		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(new ApiResponse("false", "Error processing decryption", "Data Does Not Match"));

		}

		return new ResponseEntity<>(new DcryptResponse("Success", "DecryptedResponse", decryptedPayload),
				HttpStatus.OK);

	}

}
