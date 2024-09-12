package com.Ipaisa.CustomExceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> errobj=new HashMap<>();
//		Map<String, String> errobj=ex.getAllErrors().stream()
//		.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		for(FieldError e:ex.getFieldErrors())
		errobj.put(e.getField(),e.getDefaultMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errobj);
	}
	
	

}
