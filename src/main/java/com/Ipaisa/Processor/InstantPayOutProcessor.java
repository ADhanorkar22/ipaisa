package com.Ipaisa.Processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.Ipaisa.Entitys.InstantPayBody;
import com.Ipaisa.Service.InstantPayService;
import com.Ipaisa.Service.InstantpayBulkPayoutService;
import com.Ipaisa.batchConfig.NonRetryableException;
import com.Ipaisa.batchConfig.ResponseDTO;
import com.Ipaisa.batchConfig.RetryableException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InstantPayOutProcessor implements ItemProcessor<InstantPayBody, ResponseDTO> {

    @Autowired
    private InstantpayBulkPayoutService instantpayservice;

public final  static List<InstantPayBody> failedTransactions = new ArrayList<>();

   
    private final static List<String> retryableStatus = Arrays.asList("TUP", "SPD", "SPE", "USM");
    private final static List<String> nonRetryableStatus = Arrays.asList("IAN", "IAB", "ISE", "RPI", "ANF", "SNA", "FAB");

    @Autowired
    private RetryTemplate retryTemplate;

    @Override
    public ResponseDTO process(InstantPayBody item) throws Exception {
      
        failedTransactions.clear();
        System.out.println("Processor initiated");

        return (ResponseDTO) retryTemplate.execute(context -> {
            try {
                String response = instantpayservice.encryptAndSendData(item);
                ObjectMapper objectMapper = new ObjectMapper();

                Map<String, Object> jsonMap = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});
                String statusCode = (String) jsonMap.get("statuscode");

                if (nonRetryableStatus.contains(statusCode)) {
                    throw new NonRetryableException("Status code indicates failure. No retry will be attempted.");
                } else if (retryableStatus.contains(statusCode)) {
                    System.err.println("Temporary issue detected with status: " + statusCode + ". Will retry.");
                    throw new RetryableException("Retrying due to temporary issue.");
                }
                System.out.println(new ResponseDTO(item.getTransferMode(),response));
                return new ResponseDTO(item.getTransferMode(),response);
            } catch (HttpClientErrorException.NotFound e) {
                // Handle 404 Not Found error without retrying
                System.err.println("404 Not Found: " + e.getMessage());
                failedTransactions.add(item);
                return "Payout proceeded, but received 404 error.";
            } catch (NonRetryableException e) {
                failedTransactions.add(item);
                System.err.println("Non-retryable error: " + e.getMessage());
                throw e;
            } catch (Exception e) {
                System.out.println("In catch of processor");
                failedTransactions.add(item);
                System.err.println("Error processing item: " + item);
                throw e;
            }
        });
    }

    public List<InstantPayBody> getFailedTransactions() {
        return failedTransactions;
    }
    
    
    
    
}