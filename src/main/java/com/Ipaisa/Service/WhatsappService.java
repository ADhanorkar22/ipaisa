package com.Ipaisa.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.Ipaisa.Entitys.InstantPayBody;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

@Service
public class WhatsappService {
	

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(InstantPayService.class.getName());

    public String encryptAndSendData(Map<String, Object> payload) throws Exception {


        String data = objectMapper.writeValueAsString(payload);

        return sendEncryptedData(data);
    }

    private String sendEncryptedData(String data) throws Exception {
    	
       
        MediaType mediaType = MediaType.parse("application/hal+json");
        RequestBody body = RequestBody.create(mediaType, data);

        Request request = new Request.Builder()
                .url("https://app.getgabs.com/whatsappbusiness/send-templated-message")
                .post(body)
                .addHeader("accept", "application/json")
             
                .addHeader("content-type", "application/hal+json")
                .build();

        // Execute the request
        try (Response response = client.newCall(request).execute()) {
            logRequest(request);
            String responseContentType = response.header("Content-Type");
            LOGGER.log(Level.INFO, "Response Content-Type: {0}", responseContentType);

            Headers headers = response.headers();
            for (int i = 0, size = headers.size(); i < size; i++) {
                String headerName = headers.name(i);
                String headerValue = headers.value(i);
                LOGGER.log(Level.INFO, "{0}: {1}", new Object[]{headerName, headerValue});
            }

            String responseBody = response.body().string();
            LOGGER.log(Level.INFO, "Response Body: {0}", responseBody);

            // Decrypt the response body
            return responseBody;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during HTTP request", e);
            return null;
        }
    }



    private void logRequest(Request request) {
        LOGGER.log(Level.INFO, "-- Request --");
        LOGGER.log(Level.INFO, "URL: {0}", request.url());
        LOGGER.log(Level.INFO, "Method: {0}", request.method());

        Headers headers = request.headers();
        for (String name : headers.names()) {
            LOGGER.log(Level.INFO, "Header: {0} - {1}", new Object[]{name, headers.get(name)});
        }

        RequestBody requestBody = request.body();
        if (requestBody != null) {
            try {
                LOGGER.log(Level.INFO, "Body: {0}", requestBodyToString(requestBody));
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error reading request body", e);
            }
        }
    }

    private String requestBodyToString(RequestBody requestBody) throws IOException {
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readUtf8();
    }
	
	
	
	

}
