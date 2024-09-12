package com.Ipaisa.batchConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.Ipaisa.Entitys.InstantPayBody;
import com.Ipaisa.Service.SetChargesInterface;


public class CSVUtils {
	
	
//
//    @Autowired
//    private SetChargesInterface setChargesInterface;
    
    
	
//	   @Value("#{jobParameters['filePath']}") 
//	    private static String filePath;
	
	private static final Double instantpaycharge=2.66;
	   
	   
	  
		   public static List<InstantPayBody> readCsvToList(String filePath) throws Exception {
			   System.out.println("File Path: " + filePath);
		        System.out.println("CSV Reader");

		        Resource resource = new UrlResource("file:" + filePath);
		        FlatFileItemReader<InstantPayBody> itemReader = new FlatFileItemReader<>();
		        itemReader.setResource(resource);
		        itemReader.setLinesToSkip(1); 
		        itemReader.setRecordSeparatorPolicy(new CustomRecordSeparatorPolicy());

		        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		        lineTokenizer.setNames("name", "payeeaccountNumber", "bankIfsc", "transferMode",
		                "transferAmount");
		        lineTokenizer.setStrict(false); 
		        BeanWrapperFieldSetMapper<InstantPayBody> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		        fieldSetMapper.setTargetType(InstantPayBody.class);

		        DefaultLineMapper<InstantPayBody> lineMapper = new DefaultLineMapper<>();
		        lineMapper.setLineTokenizer(lineTokenizer);
		        lineMapper.setFieldSetMapper(fieldSetMapper);

		        itemReader.setLineMapper(lineMapper);
		        itemReader.open(new ExecutionContext());

		        List<InstantPayBody> records = new ArrayList<>();
		        String line;
		        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
		            reader.readLine(); // Skip header
		            while ((line = reader.readLine()) != null) {
		                String[] fields = line.split(",");
		                if (fields.length == 5) { // Ensure correct number of columns
		                    InstantPayBody record = lineMapper.mapLine(line, 0);
		                    if (record != null && record.getPayeeaccountNumber() != null && !record.getPayeeaccountNumber().isEmpty()) {
		                        System.out.println("add");
		                        System.out.println(record.toString());
		                        records.add(record);
		                    }
		                } else {
		                    System.out.println("Skipping line with extra fields: " + line);
		                }
		            }
		        }
		        itemReader.close();
		        return records;
		    }
	    
	    public static double calculateTotalAmount(List<InstantPayBody> records,SetChargesInterface setChargesInterface) {
	    	System.out.println("in sum");
	    	System.out.println(records.size());
	    	for(InstantPayBody list:records) {
	    		if(list==null) {
	    			System.out.println(list.toString());
	    			System.out.println("charges===>"+setChargesInterface.getFinalCharge(Double.valueOf(list.getTransferAmount()),list.getTransferMode()));
	    		}
	    	}
	        Double sum= records.stream()
	                .mapToDouble(record -> Double.parseDouble(record.getTransferAmount())+setChargesInterface.getFinalCharge(Double.valueOf(record.getTransferAmount()),record.getTransferMode()))
	                .sum();
	        
	        
	        System.out.println("sum=======>"+sum);
	       return sum;
	        
	    }
	   }
	
	


