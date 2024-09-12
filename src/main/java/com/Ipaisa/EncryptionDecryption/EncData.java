package com.Ipaisa.EncryptionDecryption;

public class EncData {

	  private String payload;
      private String key;

      public EncData(String payload, String key) {
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


