package com.Ipaisa.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Ipaisa.Entitys.ApiResponse;
import com.Ipaisa.Entitys.MobileOtp;
import com.Ipaisa.Entitys.OTPTime;
import com.Ipaisa.Entitys.WhatsappDto;
import com.Ipaisa.dto.WhatsAppOTP;

@Service
public class OTPMessageService implements OTPMessageServiceInterface, WhatsAppInterface {
	@Value("${whatsapp.api_key}")
	private String apikey;

	Map<String, OTPTime> mobilemap = new HashMap<String, OTPTime>();
	public static final int VALIDITY_MINUTES = 5;

	public Map<String, Object> constructOTPMessage(WhatsAppOTP req) {
		String otp = genrateOtp(req.getMobileNumber());

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("to", req.getMobileNumber());
		data.put("type", OTPMessageServiceInterface.type1);
		data.put("sender", OTPMessageServiceInterface.sender);
		data.put("api_key", apikey);
		Map<String, Object> template = new HashMap<>();

		template.put("name", OTPMessageServiceInterface.name);
		Map<String, Object> language = new HashMap<>();
		language.put("code", OTPMessageServiceInterface.code);
		template.put("language", language);

		Map<String, Object> componentmap = new HashMap<>();

		List<Map<String, Object>> components = new ArrayList<>();

		Map<String, Object> insidecomponentslist = new HashMap<>();
		insidecomponentslist.put("type", OTPMessageServiceInterface.bodyType);

		List<Map<String, Object>> parameters = new ArrayList<>();
		parameters.add(createParameterMap(otp, "text"));
		insidecomponentslist.put("parameters", parameters);
		components.add(insidecomponentslist);
		// componentmap.put("", parameters);

		Map<String, Object> obj2 = new HashMap<>();
		obj2.put("type", "button");
		obj2.put("index", 0);
		obj2.put("sub_type", OTPMessageServiceInterface.sub_type);
		List<Map<String, Object>> parameters2 = new ArrayList<>();
		parameters2.add(createParameterMap(otp, "text"));
		obj2.put("parameters", parameters2);
		// components.add(insidecomponentslist);
		components.add(obj2);
		// componentmap.put("components", components);
		template.put("components", components);
		data.put("template", template);
		data.put("campaign_id", OTPMessageServiceInterface.campaign_id);
		data.put("recipient_type", OTPMessageServiceInterface.recipient_type);
		data.put("messaging_product", OTPMessageServiceInterface.messaging_product);

		return data;
	}

	private Map<String, Object> createParameterMap(String text, String type) {
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("text", text);
		parameter.put("type", type);
		return parameter;
	}

	public Map<String, Object> constructWhatsaAppMessage(WhatsappDto req) {

		Map<String, Object> body = new HashMap<>();
		body.put("to", req.getTo());
		body.put("api_key", apikey);
		body.put("type", WhatsAppInterface.type1);
		body.put("sender", WhatsAppInterface.sender);
		body.put("campaign_id", WhatsAppInterface.campaign_id);
		body.put("recipient_type", WhatsAppInterface.recipient_type);
		body.put("messaging_product", WhatsAppInterface.messaging_product);
		Map<String, Object> template = new HashMap<>();
		template.put("name", WhatsAppInterface.name);
		Map<String, Object> language = new HashMap<>();
		language.put("code", WhatsAppInterface.code);
		template.put("language", language);

		List<Map<String, Object>> components = new ArrayList<>();

		Map<String, Object> headerComponent = new HashMap<>();
		headerComponent.put("type", WhatsAppInterface.type2);

		List<Map<String, Object>> headerParameters = new ArrayList<>();

		Map<String, Object> imageParameter = new HashMap<>();
		imageParameter.put("type", WhatsAppInterface.type3);
		Map<String, String> image = new HashMap<>();
		image.put("link", "https://i.ibb.co/yP0gJWw/i-Paisa-Logo2.png");
		imageParameter.put("image", image);
		headerParameters.add(imageParameter);

		headerComponent.put("parameters", headerParameters);
		components.add(headerComponent);

		Map<String, Object> bodyComponent = new HashMap<>();
		bodyComponent.put("type", WhatsAppInterface.type4);

		List<Map<String, Object>> bodyParameters = new ArrayList<>();
		bodyParameters.add(createParameterMap(req.getText1(), WhatsAppInterface.type5));
		bodyParameters.add(createParameterMap(req.getText2(), WhatsAppInterface.type6));
		bodyParameters.add(createParameterMap(req.getText3(), WhatsAppInterface.type7));

		bodyComponent.put("parameters", bodyParameters);
		components.add(bodyComponent);

		template.put("components", components);

		body.put("template", template);
		return body;

	}

	public String genrateOtp(String mobileno) {
		Random random = new Random();
		String otp = String.valueOf(random.nextInt(9000) + 100000);
		mobilemap.put(mobileno, new OTPTime(otp, LocalDateTime.now()));
		return otp;

	}

	public Boolean validateOtp(MobileOtp motp) {
		System.out.println("validateOtp=====> Size of mobilemap: " + mobilemap.size());

		String mobileno = motp.getMobileno();
		String otp = motp.getOtp();
		OTPTime otpData = mobilemap.get(mobileno);

		if (otpData != null && otpData.getOtp().equals(otp)) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime timestamp = otpData.getTime();

			long minutesElapsed = timestamp.until(now, ChronoUnit.MINUTES);

			mobilemap.remove(mobileno);
			return minutesElapsed <= VALIDITY_MINUTES;
		}
		mobilemap.remove(mobileno);
		return false;
	}

	@Scheduled(fixedRate = 360000)
	public void removeExpiredOTPs() {
		LocalDateTime now = LocalDateTime.now();
		mobilemap.entrySet()
				.removeIf(entry -> ChronoUnit.MINUTES.between(entry.getValue().getTime(), now) > VALIDITY_MINUTES);
		System.out.println("Expired OTPs removed. Current map size: " + mobilemap.size());
	}

}
