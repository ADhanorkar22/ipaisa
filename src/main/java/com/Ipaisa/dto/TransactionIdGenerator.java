package com.Ipaisa.dto;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
public class TransactionIdGenerator {

	
	  private static final Random RANDOM = new Random();
	    private static final AtomicLong COUNTER = new AtomicLong(1);

	    public static String generateTransactionId() {
	        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
	        int randomNum = 1000000 + RANDOM.nextInt(9000000); // 6-digit random number
	        long counterValue = COUNTER.getAndIncrement();
	        return timestamp + randomNum + counterValue; 
	    }
}
