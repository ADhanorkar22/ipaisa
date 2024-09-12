package com.Ipaisa.mplan;

import org.springframework.http.ResponseEntity;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class RechargePlans {
	private static final Logger logger = LoggerFactory.getLogger(RechargePlans.class);
    // Define OkHttpClient instance
    private final OkHttpClient client = new OkHttpClient().newBuilder().build();
    // Method to fetch plan details
    public String fetchPlanDetails() throws IOException {
        // Construct the URL dynamically
        String url = String.format("https://www.mplan.in/api/plans.php?apikey=%s&offer=%s&tel=%s&operator=%s",
                                   "7bcccd451342fae1ae498f91c20d6110", "roffer", "7798552844", "Airtel");
        // Define media type and request body (if necessary, here it's empty as GET does not need a body)
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        // Create the request object
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null) // Use null for GET requests, no body needed
                .build();
        // Execute the request and return the response body as string
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                logger.error("Failed to fetch plan details, Response Code: " + response.code());
                throw new IOException("Unexpected code " + response);
            }
            // Log and return the response body
            String responseBody = response.body().string();
            logger.info("Response Payload: " + responseBody);
            return responseBody;
        }
    }
}