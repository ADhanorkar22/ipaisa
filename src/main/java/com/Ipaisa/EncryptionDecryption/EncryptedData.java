package com.Ipaisa.EncryptionDecryption;

public class EncryptedData {
	  private final String payload;
      private final String key;

      public EncryptedData(String payload, String key) {
          this.payload = payload;
          this.key = key;
      }

      public String getPayload() {
          return payload;
      }

      public String getKey() {
          return key;
      }
  
}
