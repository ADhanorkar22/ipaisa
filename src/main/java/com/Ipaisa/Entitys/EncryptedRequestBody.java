package com.Ipaisa.Entitys;

public class EncryptedRequestBody	 {
    private final String payload;
    private final String key;
    private final String partnerId;
    private final String clientid;

    public EncryptedRequestBody(String payload, String key, String partnerId, String clientid) {
        this.payload = payload;
        this.key = key;
        this.partnerId = partnerId;
        this.clientid = clientid;
    }

    public String getPayload() {
        return payload;
    }

    public String getKey() {
        return key;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getClientid() {
        return clientid;
    }
}