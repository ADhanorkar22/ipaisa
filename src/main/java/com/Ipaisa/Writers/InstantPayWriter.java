package com.Ipaisa.Writers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.Ipaisa.Entitys.InstantPayBody;
import com.Ipaisa.Entitys.InstantPayOut;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Repository.InstantPayoutRepository;
import com.Ipaisa.Repository.UserRepositery;
import com.Ipaisa.Service.InstantPayService;
import com.Ipaisa.Service.SetChargesInterface;
import com.Ipaisa.batchConfig.ResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Service
public class InstantPayWriter implements ItemWriter<ResponseDTO>, StepExecutionListener {

    private String mobileNumber;
    
    @Autowired
   	private UserRepositery uRepo;
    
    @Autowired
    private SetChargesInterface setChargesInterface;
    
    
    
    @Autowired
    private InstantPayoutRepository instantPayoutService;
    private static final Logger LOGGER = Logger.getLogger(InstantPayService.class.getName());
  

    @Override
    public void beforeStep(StepExecution stepExecution) {
       
        JobParameters jobParameters = stepExecution.getJobParameters();
        this.mobileNumber = jobParameters.getString("mobileNumber");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
     
    @Override
    public void write(Chunk<? extends ResponseDTO> chunk) throws Exception {
    	System.out.println("in writter======>"+mobileNumber);
    	Double ourCharge=0.0;
        for (ResponseDTO item : chunk) {
        System.out.println("item====>"+item);
        	  ObjectMapper objectMapper = new ObjectMapper();
              try {
                  Map<String, Object> jsonMap = objectMapper.readValue(item.getResponse(), new TypeReference<Map<String, Object>>() {});
                  Map<String, Object> data1 = (Map<String, Object>) jsonMap.get("data");

                  // Extract values from JSON
                  InstantPayOut instantPayoyt = new InstantPayOut();
                  User user=uRepo.findByMobileNumber(mobileNumber);
                  System.out.println("user======>"+user);
                  instantPayoyt.setUser(user);
                  instantPayoyt.setStatuscode((String) jsonMap.get("statuscode"));
                  instantPayoyt.setActcode((String) jsonMap.get("actcode"));
                  instantPayoyt.setStatus((String) jsonMap.get("status"));
                  instantPayoyt.setExternalRef((String) data1.get("externalRef"));
                  instantPayoyt.setPoolReferenceId((String) data1.get("poolReferenceId"));
                  instantPayoyt.setTxnValue((String) data1.get("txnValue"));
                  instantPayoyt.setTxnReferenceId((String) data1.get("txnReferenceId"));

                  Map<String, Object> pool = (Map<String, Object>) data1.get("pool");
                  if (pool != null) {
                      instantPayoyt.setPoolAccount((String) pool.get("account"));
                      instantPayoyt.setPoolOpeningBal((String) pool.get("openingBal"));
                      instantPayoyt.setPoolMode((String) pool.get("mode"));
                      instantPayoyt.setPoolAmount((String) pool.get("amount"));
                      instantPayoyt.setPoolClosingBal((String) pool.get("closingBal"));
                  }

                  Map<String, Object> payer = (Map<String, Object>) data1.get("payer");
                  if (payer != null) {
                      instantPayoyt.setPayerAccount((String) payer.get("account"));
                      instantPayoyt.setPayerName((String) payer.get("name"));
                  }

                  Map<String, Object> payee = (Map<String, Object>) data1.get("payee");
                  if (payee != null) {
                      instantPayoyt.setPayeeAccount((String) payee.get("account"));
                      instantPayoyt.setPayeeName((String) payee.get("name"));
                  }
                  String timestampString = (String) jsonMap.get("timestamp");
                  if (timestampString != null && !timestampString.isEmpty()) {
                      try {
                          LocalDateTime timestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                          instantPayoyt.setTimestamp(timestamp);
                      } catch (DateTimeParseException e) {
                          System.err.println("Error parsing timestamp: " + timestampString);
                          e.printStackTrace();
                      }
                  }
                  instantPayoyt.setIpayUuid((String) jsonMap.get("ipay_uuid"));
                  instantPayoyt.setOrderid((String) jsonMap.get("orderid"));
                  instantPayoyt.setEnvironment((String) jsonMap.get("environment"));
       ////////////////// Update Wallet Balance /////////////////////////////////
                  instantPayoyt.setWalletOpeningBalance(user.getWalletBalance());
                  String amt=(String) data1.get("txnValue");
                  double amount=Double.parseDouble(amt);
//                  double percentAmount=amount* 2.66 /100;
             
               ourCharge=setChargesInterface.getFinalCharge(amount,item.getTransferMode());
                  double finalAmount=amount+ourCharge;
                  
                  String status=(String) jsonMap.get("status");
                  if("Transaction Successful".equals(status))
                  {
                  	user.setWalletBalance(user.getWalletBalance()-finalAmount);
                  	uRepo.save(user);
                  }
                  instantPayoyt.setWalletClosingBalance(user.getWalletBalance());
                  instantPayoutService.save(instantPayoyt);
        	 
            System.out.println("Processing item with mobile number: " + mobileNumber);
            
        } catch (Exception e) {
           e.getStackTrace();
           
        }
        }
    }
    }

