package com.Ipaisa.UserController;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Ipaisa.Entitys.InstantPayBody;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Jwt_Utils.JwtUtils;
import com.Ipaisa.Repository.UserRepositery;
import com.Ipaisa.Service.SetChargesInterface;
import com.Ipaisa.batchConfig.CSVUtils;

import java.io.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.*;
import java.util.List;
@RestController
@RequestMapping("/auth")
public class InstantPayBatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("firstJob")
    private Job job;
    
    @Autowired
    private UserDetailsService udeatils;
    
    @Autowired
    private JwtUtils utils;
    
    @Autowired
    private UserRepositery uRepo;

  
    
    @Autowired
    private UserRepositery userrepo;
    
    
  @Autowired
  private SetChargesInterface setChargesInterface;
  
    
    
    
    @PostMapping("/launchjob")
    public ResponseEntity<String> startBatch(@RequestHeader("Authorization") String token, @RequestParam("file") MultipartFile file/* @PathVariable("id") String id*/) throws NoSuchAlgorithmException {
        Path tempFilePath = null;
        List<InstantPayBody> records=null;
        User user=null;
        try {

            String t = token.startsWith("Bearer ") ? token.substring(7) : null;
            String username = utils.getUserNameFromJwtToken(t);
            UserDetails userDetails = udeatils.loadUserByUsername(username);
            String mobileno = userDetails.getUsername();
            user=userrepo.findByMobileNumber(mobileno);
            
            File tempFile = File.createTempFile("upload-", ".csv");
            tempFilePath = tempFile.toPath();
            Files.copy(file.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);
           
			try {
				 records = CSVUtils.readCsvToList(tempFilePath.toString());
			} catch (Exception e) {
				//System.out.println("file reeor");
				
				  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing file");
			}
            double totalAmount = CSVUtils.calculateTotalAmount(records,setChargesInterface);
            System.out.println("final amount===> "+totalAmount);

            if (totalAmount >user.getWalletBalance() ) {
            	 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Total amount exceeds predefined balance");
                
            }
          
            String fileHash = calculateFileHash(tempFilePath);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("filePath", tempFilePath.toString())
                    .addString("mobileNumber", mobileno)
                    .addString("userId",user.getUserid() )
                  //  .addString("id", id)
                    .addString("fileHash", fileHash)
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
            
            

          
            return ResponseEntity.status(HttpStatus.OK).body("Batch Started");
        } catch (IOException | JobExecutionAlreadyRunningException | JobRestartException |
                 JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
           
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Batch Failed");
        }finally {
            try {
                if (tempFilePath != null) {
                	System.out.println("to delete file");
                    Files.deleteIfExists(tempFilePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private String calculateFileHash(Path filePath) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        try (InputStream is = Files.newInputStream(filePath); DigestInputStream dis = new DigestInputStream(is, digest)) {
            byte[] buffer = new byte[4096];
            while (dis.read(buffer) != -1) {
                // Reading file data and updating the hash
            }
        }
        byte[] hashBytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}