package com.Ipaisa.DeepLink;

import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
@Service
public class EService {
	  public static String compress(String data) throws Exception {
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
	            gzipOutputStream.write(data.getBytes("UTF-8"));
	        }
	        byte[] compressedData = byteArrayOutputStream.toByteArray();
	        return Base64.getEncoder().encodeToString(compressedData);
	    }
}

