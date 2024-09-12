package com.Ipaisa.UserController;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Ipaisa.Entitys.User;
import com.Ipaisa.Jwt_Utils.JwtUtils;
import com.Ipaisa.Repository.UserRepositery;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import java.nio.file.FileSystemException;


@RestController
@RequestMapping("/auth")
public class BadRecordController {


//	 // private static final Logger logger = LoggerFactory.getLogger(BadRecordController.class);
//
//    // Root location for bad records
//    private final Path rootLocation = Paths.get(System.getProperty("java.io.tmpdir"), "badrecords");
//
//    @GetMapping("/badrecords/{userid}")
//    public ResponseEntity<?> getBadRecords(@PathVariable String userid) {
//      //  logger.info("Getting file for user: {}", userid);
//        try {
//            // Construct the user directory path
//            Path userDirectory = rootLocation.resolve(userid);
//
//            // Check if directory exists
//            if (Files.exists(userDirectory) && Files.isDirectory(userDirectory)) {
//                File[] files = userDirectory.toFile().listFiles();
//
//                if (files != null && files.length > 0) {
//                    // Assuming we need to send the first file found
//                    File fileToSend = files[0];
//                    Resource resource = new FileSystemResource(fileToSend);
//
//                    // Prepare response entity
//                    ResponseEntity<Resource> response = ResponseEntity.ok()
//                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileToSend.getName() + "\"")
//                            .body(resource);
//                 //System.out.println(   response.getBody().toString());
//
//                    // Attempt to delete the file after sending
//                    deleteFileWithRetry(fileToSend.toPath());
//
//                    return response;
//                } else {
//                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                            new ErrorResponse("No files found for user: " + userid));
//                }
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                        new ErrorResponse("User directory not found: " + userid));
//            }
//        } catch (IOException e) {
//          //  logger.error("Error retrieving file", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//                    new ErrorResponse("Error retrieving file."));
//        }
//    }
//
//    private void deleteFileWithRetry(Path filePath) throws IOException {
//        int maxRetries = 5;
//        int retryDelayMs = 1000;
//        int attempt = 0;
//        boolean deleted = false;
//
//        while (attempt < maxRetries && !deleted) {
//            try {
//                Files.delete(filePath);
//                deleted = true;
//              //  logger.info("File deleted successfully: {}", filePath);
//                System.out.println("file deleted");
//            } catch (IOException e) {
//                if (e instanceof java.nio.file.FileSystemException) {
//                  //  logger.warn("Attempt {}: File is currently in use or cannot be deleted: {}", attempt + 1, filePath);
//                } else {
//                    throw e; // Rethrow if it's not a FileSystemException
//                }
//                attempt++;
//                try {
//                    Thread.sleep(retryDelayMs);
//                } catch (InterruptedException ie) {
//                    Thread.currentThread().interrupt();
//                }
//            }
//        }
//
//        if (!deleted) {
//          //  logger.error("Failed to delete the file after {} attempts: {}", maxRetries, filePath);
//        }
//    }
//	
//	 private final ObjectMapper objectMapper = new ObjectMapper();
//
//	    @Value("${bad.records.directory:badrecords}")
//	    private String badRecordsDirectory;
//
//	    @GetMapping("/badrecords/{userid}")
//	    public ResponseEntity<?> getFailedRecords(@PathVariable String userid) throws IOException {
//// Construct the file path
//			 File file = Paths.get(badRecordsDirectory, userid, "failed_records.csv").toFile();
//
//			 // Check if the file exists and is a file
//			 if (!file.exists() || !file.isFile()) {
//			     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User directory or file not found: " + userid));
//			 }
//
//			 // Prepare the resource
//			 Resource resource = new FileSystemResource(file);
//
//			 // Prepare the response entity
//			 ResponseEntity<Resource> response = ResponseEntity.ok()
//			         .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
//			         .body(resource);
//
//			 // Attempt to delete the file after sending
//         //  deleteFileWithRetry(file.toPath());
//
//			 return response;
//	     }
//	
	
	
	
	/////above working
	
	
	
//	 private final ObjectMapper objectMapper = new ObjectMapper();
//
//	    @Value("${bad.records.directory:badrecords}")
//	    private String badRecordsDirectory;
//
//	    // private static final Logger logger = LoggerFactory.getLogger(FailedRecordsController.class);
//
//	    @GetMapping("/badrecords/{userid}")
//	    public ResponseEntity<?> getBadRecords(@PathVariable String userid) {
//	        // logger.info("Getting file for user: {}", userid);
//	        try {
//	            // Construct the file path
//	            Path userDirectory = Paths.get(badRecordsDirectory, userid);
//
//	            // Check if directory exists
//	            if (Files.exists(userDirectory) && Files.isDirectory(userDirectory)) {
//	                File[] files = userDirectory.toFile().listFiles();
//
//	                if (files != null && files.length > 0) {
//	                    // Assuming we need to send the first file found
//	                    File fileToSend = files[0];
//	                    Resource resource = new FileSystemResource(fileToSend);
//
//	                    // Prepare response entity
//	                    ResponseEntity<Resource> response = ResponseEntity.ok()
//	                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileToSend.getName() + "\"")
//	                            .body(resource);
//
//	                    // Attempt to delete the file after sending
//	                    deleteFileWithRetry(fileToSend.toPath());
//
//	                    return response;
//	                } else {
//	                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//	                            new ErrorResponse("No files found for user: " + userid));
//	                }
//	            } else {
//	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//	                        new ErrorResponse("User directory not found: " + userid));
//	            }
//	        } catch (IOException e) {
//	            // logger.error("Error retrieving file", e);
//	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//	                    new ErrorResponse("Error retrieving file."));
//	        }
//	    }
//
//	    private void deleteFileWithRetry(Path filePath) throws IOException {
//	        int maxRetries = 5;
//	        int retryDelayMs = 1000;
//	        int attempt = 0;
//	        boolean deleted = false;
//
//	        while (attempt < maxRetries && !deleted) {
//	            try {
//	                Files.delete(filePath);
//	                deleted = true;
//	                // logger.info("File deleted successfully: {}", filePath);
//	                System.out.println("File deleted");
//	            } catch (IOException e) {
//	                if (e instanceof java.nio.file.FileSystemException) {
//	                    // logger.warn("Attempt {}: File is currently in use or cannot be deleted: {}", attempt + 1, filePath);
//	                } else {
//	                    throw e; // Rethrow if it's not a FileSystemException
//	                }
//	                attempt++;
//	                try {
//	                    Thread.sleep(retryDelayMs);
//	                } catch (InterruptedException ie) {
//	                    Thread.currentThread().interrupt();
//	                }
//	            }
//	        }
//
//	        if (!deleted) {
//	            // logger.error("Failed to delete the file after {} attempts: {}", maxRetries, filePath);
//	        }
//	    }
	
	  @Autowired
		private UserDetailsService udeatils;
	    @Autowired
	    private JwtUtils utils;
	    
	    @Autowired
	    private UserRepositery uRepo;
	

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${bad.records.directory:badrecords}")
    private String badRecordsDirectory;

    @GetMapping("/badrecords")
    public ResponseEntity<?> getBadRecords(@RequestHeader("Authorization") String token) throws IOException {
      List<String> badrecords=null;
      Map<String,List<String>> records=null;
    	 
         	String t=null;
         	String userid=null;
         	User user=null;
       	
       	if (token.startsWith("Bearer ")) {
   		        t = token.substring(7);
   		        System.out.println(t);
   		   }          
   		   String username = utils.getUserNameFromJwtToken(t);
   	        UserDetails userDetails = udeatils.loadUserByUsername(username);
   	        String mobileno=userDetails.getUsername();
   	        user=uRepo.findByMobileNumber(mobileno);
   	        userid=user.getUserid();
   	        
		Path userDirectory = Paths.get(badRecordsDirectory, userid);

		
		if (Files.exists(userDirectory) && Files.isDirectory(userDirectory)) {
		    File[] files = userDirectory.toFile().listFiles();

		    
		    if (files != null && files.length > 0) {
		        // Assuming we need to send the first file found
		        File fileToSend = files[0];
		        Scanner Reader = new Scanner(fileToSend);
		        badrecords=new ArrayList<String>();
		        records=new HashMap<String, List<String>>();
	            while (Reader.hasNextLine()) {
	                String data = Reader.nextLine();
	                if(!data.equals(null)&&data!=null) {
	                System.out.println("data====>"+data);
	               badrecords.add(data);
	                }
	               
	            }
	            records.put("badRecords", badrecords);
	            Reader.close();
	        
		      //  Resource resource = new FileSystemResource(fileToSend);

		        // Prepare response entity
		        ResponseEntity<Map<String, List<String>>> response = ResponseEntity.ok()
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileToSend.getName() + "\"")
		                .body(records);

		        // Attempt to delete the file after sending
		        deleteFileWithRetry(fileToSend.toPath());
		       // System.out.println(resource.toString());
		        //System.out.println(response.getHeaders().toString()+" "+response.getBody().toString());
		      
		        return response;
		    } else {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
		                new ErrorResponse("No files found for user: " + userid));
		    }
		} else {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
		            new ErrorResponse("User directory not found: " + userid));
		}
    	 
    }

    private void deleteFileWithRetry(Path filePath) throws IOException {
        int maxRetries = 0;
        int retryDelayMs = 1000;
        int attempt = 0;
        boolean deleted = false;

        while (attempt <= maxRetries && !deleted) {
            try {
                Files.delete(filePath);
                deleted = true;
                // logger.info("File deleted successfully: {}", filePath);
                System.out.println("File deleted");
            } catch (FileSystemException e) {
                // logger.warn("Attempt {}: File is currently in use or cannot be deleted: {}", attempt + 1, filePath);
                attempt++;
                try {
                    Thread.sleep(retryDelayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (!deleted) {
            // logger.error("Failed to delete the file after {} attempts: {}", maxRetries, filePath);
            throw new IOException("Failed to delete the file after " + maxRetries + " attempts");
        }
    }
	
	
	
}