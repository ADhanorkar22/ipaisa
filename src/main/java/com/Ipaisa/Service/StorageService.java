package com.Ipaisa.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Ipaisa.Entitys.TicketRaise;
import com.Ipaisa.Entitys.UserKycDetail;
import com.Ipaisa.Repository.UserKycRepositery;
import com.Ipaisa.Repository.UserRepositery;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StorageService implements IStorageService {

    @Value("${bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3client;
    
    @Autowired
    private UserKycRepositery kycRepo;
    
    @Autowired
    private UserRepositery userrepo;

    @Override
//    public void uploadFile(MultipartFile file,String id,String name) {
//    	System.out.println(id);
//    	String filename=null;
//    	UserKycDetail ukyc=kycRepo.findByUid(id);
//    	System.out.println(ukyc);
//    	
//    	System.out.println("hererr!!!");
//    	if(file.isEmpty()||file==null) {
//    		System.out.println("empty");
//    	
//    	}
//        File fileObj = convertMultiPartFileToFile(file);
//        if(name=="photo") {
//        	System.out.println("photo");
//        filename = "photos/"+id+file.getOriginalFilename();
//        ukyc.setPassportFilePath(filename);
//        }
//        if(name=="aadhaar") {
//            filename = "Adharcards/"+id+file.getOriginalFilename();
//            ukyc.setAadhaarFilePath(filename);
//            }
//        if(name=="pan") {
//            filename = "PanCards/"+id+file.getOriginalFilename();
//            ukyc.setPanFilePath(filename);
//            }
//        if(name=="agreement") {
//            filename = "Agreements/"+id+file.getOriginalFilename();
//            ukyc.setAgreementFilePath(filename);
//            }
//        
//        //System.out.println(filename);
//       
//       
//        kycRepo.save(ukyc);
//        PutObjectResult putobj= s3client.putObject(new PutObjectRequest(bucketName, filename, fileObj));
//        System.out.println(putobj);
//        fileObj.delete();
//        //return putobj.getContentMd5();//filename + " file uploaded";
//    }
    
    public void uploadFile(MultipartFile file, String id, String name) {
        System.out.println(id);
        String filename = null;
        UserKycDetail ukyc = kycRepo.findByUid(id);
        System.out.println(ukyc);

        if (file.isEmpty()) {
            System.out.println("empty");
            return;
        }
        
        File fileObj = convertMultiPartFileToFile(file);
        switch (name) {
            case "photo":
                System.out.println("photo");
                filename = "photos/" + id + file.getOriginalFilename();
                ukyc.setPassportFilePath(filename);
                break;
            case "aadhaar":
                filename = "Adharcards/" + id + file.getOriginalFilename();
                ukyc.setAadhaarFilePath(filename);
                break;
            case "pan":
                filename = "PanCards/" + id + file.getOriginalFilename();
                ukyc.setPanFilePath(filename);
                break;
            case "agreement":
                filename = "Agreements/" + id + file.getOriginalFilename();
                ukyc.setAgreementFilePath(filename);
                break;
        }

        kycRepo.save(ukyc);
        PutObjectResult putobj = s3client.putObject(new PutObjectRequest(bucketName, filename, fileObj));
        System.out.println(putobj);
        fileObj.delete();
    }
//////////////////////////////////////////////UTR///////////////////////////////////////
    
    @Override
    public TicketRaise uploadFile(MultipartFile file,TicketRaise tr) {
    	//System.out.println(id);
    	
    	System.out.println("hererr!!!");
    	if(file.isEmpty()||file==null) {
    		System.out.println("empty");
    	
    	}
        File fileObj = convertMultiPartFileToFile(file);
        String filename = "UTRFiles/"+tr.getUser().getUserid()+file.getOriginalFilename();
        //System.out.println(filename);
//        UserKycDetail ukyc=kycRepo.findByUid(id);
//        ukyc.setAadhaarFilePath(filename);
//        kycRepo.save(ukyc);
         tr.setReceiptPath(filename);
        PutObjectResult putobj= s3client.putObject(new PutObjectRequest(bucketName, filename, fileObj));
        
        
        fileObj.delete();
        return  tr;//putobj.getContentMd5();//filename + " file uploaded";
    }

    
    
    
    
    
    //////////////////////////////////////////////////////////////////////
    @Override
    public byte[] downloadFile(String id) {
    	System.out.println("download in"+id);
    	 UserKycDetail ukyc=kycRepo.findByUid(id);
    	 System.out.println(ukyc);
    	 String filename=ukyc.getPassportFilePath();
    	 System.out.println(filename);
        com.amazonaws.services.s3.model.S3Object s3object = s3client.getObject(bucketName,filename);
        S3ObjectInputStream inputstream = s3object.getObjectContent();
        System.out.println(inputstream.toString());
        try {
            byte[] content = IOUtils.toByteArray(inputstream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deleteFile(String filename) {
        s3client.deleteObject(bucketName, filename);
        return filename + " removed!!!";
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream os = new FileOutputStream(convertedFile)) {
            os.write(file.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }

	@Override
	public ResponseEntity<?> uploadFiles(MultipartFile photo, MultipartFile pan, MultipartFile aadhaar,
			MultipartFile agreement, String u) {
		
		String userid=userrepo.findByMobileNumber(u).getUserid();
		System.out.println("---------"+userid);
		
//		String files=" ,";
//		
//		if(photo!=null) {
//			System.out.println("photo");
//			uploadFile(photo,userid,"photo");
//			files=photo.getName()+files;
//		}
//		if(pan!=null) {
//			System.out.println("pan");
//			uploadFile(pan,userid,"pan");
//			files+=photo.getName();
//		}
//		if(aadhaar!=null) {
//			System.out.println("aa");
//			uploadFile(aadhaar,userid,"aadhaar");
//			files+=photo.getName();
//		}
//		if(agreement!=null) {
//			System.out.println("ag");
//			uploadFile(agreement,userid,"agreement");
//			files+=photo.getName();
//		}
//	
//		return ResponseEntity.status(HttpStatus.SC_OK).body("files uploaded successfully");
		
	    StringBuilder files = new StringBuilder();
	    
	    if (photo != null && !photo.isEmpty()) {
	        System.out.println("photo");
	        uploadFile(photo, userid, "photo");
	        files.append(photo.getName()).append(", ");
	    }
	    if (pan != null && !pan.isEmpty()) {
	        System.out.println("pan");
	        uploadFile(pan, userid, "pan");
	        files.append(pan.getName()).append(", ");
	    }
	    if (aadhaar != null && !aadhaar.isEmpty()) {
	        System.out.println("aadhaar");
	        uploadFile(aadhaar, userid, "aadhaar");
	        files.append(aadhaar.getName()).append(", ");
	    }
	    if (agreement != null && !agreement.isEmpty()) {
	        System.out.println("agreement");
	        uploadFile(agreement, userid, "agreement");
	        files.append(agreement.getName()).append(", ");
	    }

	    if (files.length() > 0) {
	        files.setLength(files.length() - 2);  // Remove the trailing comma and space
	        return ResponseEntity.status(HttpStatus.SC_OK).body("Files uploaded successfully: " + files.toString());
	    } else {
	        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body("No files to upload");
	    }
}
}