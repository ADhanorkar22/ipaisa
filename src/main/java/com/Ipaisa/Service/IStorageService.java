package com.Ipaisa.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Ipaisa.Entitys.TicketRaise;

public interface IStorageService {
	void uploadFile(MultipartFile file,String id,String name);
	ResponseEntity<?>uploadFiles(MultipartFile photo, MultipartFile pan, MultipartFile aadhaar, MultipartFile agreement, String userid);
	 byte[] downloadFile(String id);
	 String deleteFile(String filename);
	// public String uploadFile(MultipartFile file,String id ,String type);
	  TicketRaise uploadFile(MultipartFile file,TicketRaise tr);
}
