package com.Ipaisa.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.Ipaisa.Entitys.ApiResponse;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Jwt_Utils.JwtUtils;
import com.Ipaisa.Repository.UserRepositery;
import com.Ipaisa.Service.IStorageService;

@RestController
@RequestMapping("/auth")
public class FileController {
	
	@Autowired
	private IStorageService istorageservice;
    @Autowired
    private JwtUtils utils;
	@Autowired
	private UserDetailsService udeatils;
	@Autowired
	private UserRepositery uRepo;
	
	
	@PostMapping("/upload")
	  public ResponseEntity<?> uploadFile(
			  @RequestParam(value = "photo", required = false) MultipartFile photo,
		        @RequestParam(value = "pan", required = false) MultipartFile pan,
		        @RequestParam(value = "aadhaar", required = false) MultipartFile aadhaar,
		        @RequestParam(value = "agreement", required = false) MultipartFile agreement,
		        @RequestHeader("Authorization") String token)
	{  
		 if (token == null || !token.startsWith("Bearer ")) {
             return ResponseEntity.badRequest().body(new ApiResponse("error", "Invalid Authorization header"));
         }
		System.out.println("ashish");
		        String t = null;
		        System.out.println(token);
		        if (token.startsWith("Bearer ")) {
		            t = token.substring(7);
		            System.out.println(t);
		        }
		        String username = utils.getUserNameFromJwtToken(t);
		        UserDetails userDetails = udeatils.loadUserByUsername(username);
		        String userid = userDetails.getUsername();
		        System.out.println(userid);

		        return new ResponseEntity<>(istorageservice.uploadFiles(photo, pan, aadhaar, agreement, userid), HttpStatus.OK);
		    }
	
	@GetMapping("/download")
	public ResponseEntity<ByteArrayResource> downloadFile(/*@PathVariable String id,*/@RequestHeader("Authorization") String token){
		 String t=null;
		   System.out.println(token);
		   if (token.startsWith("Bearer ")) {
		        t = token.substring(7);
		        System.out.println(t);
		        }
		   String username = utils.getUserNameFromJwtToken(t);
	        UserDetails userDetails = udeatils.loadUserByUsername(username);
	        String mobileNo=userDetails.getUsername();
	        User u=this.uRepo.findByMobileNumber(mobileNo);
	        System.out.println("UserIDDonload--------"+u.getUserid()); 
	        String userid=u.getUserid();
	        System.out.println(userid);
		byte[] file=istorageservice.downloadFile(userid);
		ByteArrayResource resource=new ByteArrayResource(file);
		return ResponseEntity.ok().contentLength(file.length).header("Content-type", "application/octet-stream").header("Content-disposition", "attachment; filename=\""+userid+"\"")
				.body(resource)
				;
	}
		@DeleteMapping("/delete/{filename}")
		public ResponseEntity<String> deleteFile(@PathVariable String filename){
			return new ResponseEntity<>(istorageservice.deleteFile(filename),HttpStatus.OK);
		}
		
		
	

}
