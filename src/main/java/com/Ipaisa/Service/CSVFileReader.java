package com.Ipaisa.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

@Service
public class CSVFileReader {
	
	
	public void processCsvFile(MultipartFile file) throws IOException, CsvException
	
	{ 
		try (Reader reader = new InputStreamReader(file.getInputStream()); 
			CSVReader csvReader = new CSVReader(reader)) 
		{
		
		List<String[]> records = csvReader.readAll(); 
		for (String[] record : records) { 
			// Process each record processRecord(record); } } catch (IOException | CsvException e) { e.printStackTrace(); }
		}
		}
	}

}
