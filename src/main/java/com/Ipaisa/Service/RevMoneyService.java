package com.Ipaisa.Service;

import org.springframework.http.ResponseEntity;
import com.Ipaisa.dto.ReverseMoneyDto;
public interface RevMoneyService {
	boolean revMoney(String adminId, ReverseMoneyDto revDto);
}