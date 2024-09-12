package com.Ipaisa.EncryptionDecryptionUtilityClass;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class dy {
	 private static final String AES_ALGORITHM = "AES";
	    private static final String AES_MODE = "AES/ECB/PKCS5Padding";
	   public static String decryptAES(String encryptedText, String secretKey) throws Exception {
	        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), AES_ALGORITHM);
	        Cipher cipher = Cipher.getInstance(AES_MODE);
	        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

	        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
	        return new String(decryptedBytes);
	    }

}
