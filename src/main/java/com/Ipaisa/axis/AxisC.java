package com.Ipaisa.axis;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/bbps")
public class AxisC {

	@Autowired
	private BillFetchService billserv;
	
//	private  ApiService1 apiService1;
//	
//	@GetMapping("/billFetch")
//    public ResponseEntity<?> fetchBill() throws Exception {
//		this.as.sendRequest();
//                 return null;
////		System.out.println("Request----->> "+request.toString());
////		return null; 
//    }
	 @Autowired
	    private ApiService1 apiService1;

	    @GetMapping("/billFetch")
	    public ResponseEntity<String> sendRequest() {
	        try {
	            apiService1.sendRequest();
	            return ResponseEntity.ok("Request sent successfully.");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).body("Failed to send request: " + e.getMessage());
	        }
	    }

}
