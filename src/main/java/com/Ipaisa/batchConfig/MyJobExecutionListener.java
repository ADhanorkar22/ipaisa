package com.Ipaisa.batchConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import com.Ipaisa.Entitys.InstantPayBody;
import com.Ipaisa.Processor.InstantPayOutProcessor;
import com.Ipaisa.listnere.MySkipListener;

public class MyJobExecutionListener implements JobExecutionListener {

    private final MySkipListener skipListener;
    private final ItemProcessor<InstantPayBody, ResponseDTO> processor;

    public MyJobExecutionListener(MySkipListener skipListener, ItemProcessor<InstantPayBody, ResponseDTO> processor) {
        this.skipListener = skipListener;
        this.processor = processor;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // No specific handling needed before job
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
    	 System.out.println("In writing bad record");

         List<InstantPayBody> failedTransactions = ((InstantPayOutProcessor) processor).getFailedTransactions();
         List<String> badRecords = skipListener.getBadRecords();

         System.out.println("Number of bad records: " + badRecords.size());
         System.out.println("Number of failed transactions: " + failedTransactions.size());

         if (!failedTransactions.isEmpty() || !badRecords.isEmpty()) {
             try {
                 String userId = jobExecution.getJobParameters().getString("userId"); // Assuming userId is passed as a job parameter
                 File userDirectory = new File("badrecords/" + userId);
                 if (!userDirectory.exists()) {
                     if (userDirectory.mkdirs()) {
                         System.out.println("Directory created successfully: " + userDirectory.getAbsolutePath());
                     } else {
                         System.err.println("Failed to create directory: " + userDirectory.getAbsolutePath());
                         return; // Exit if directory creation failed
                     }
                 }

                 File file = new File(userDirectory, "failed_records.csv");
                 try (FileWriter writer = new FileWriter(file)) { // Open FileWriter in append mode
                     System.out.println("Writing to file: " + file.getAbsolutePath());

//                     if (file.length() > 0) { // Check if file is not empty
//                         writer.write("\n\n"); // Write two blank lines to separate records
//                     }

                     for (String record : badRecords) {
                         if (record != null) {
                             System.out.println("Writing bad record: " + record);
                             writer.write(record + "\n");
                         } else {
                             System.out.println("Skipping null or blank bad record.");
                         }
                     }

                     for (InstantPayBody transaction : failedTransactions) {
                         if (transaction != null) {
                        	 
                        	 System.out.println("in writting bad transaction while processing :");
                             System.out.println("Writing failed transaction: " + transaction);
                             writer.write(transaction.toString() + "\n");
                         } else {
                             System.out.println("Skipping null failed transaction.");
                         }
                     }
                 } catch (IOException e) {
                     System.err.println("Error while writing to the file.");
                     e.printStackTrace();
                 }
             } catch (Exception e) {
                 System.err.println("Error in directory creation or file writing.");
                 e.printStackTrace();
             }
             
             finally {
            	 InstantPayOutProcessor.failedTransactions.clear();
            	 MySkipListener.badRecords.clear();
             }
         } else {
             System.out.println("No failed transactions or bad records to write.");
         }
     }
}