package com.Ipaisa.batchConfig;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.database.JpaItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.beans.factory.annotation.Value;
//import com.Ipaisa.Entitys.InstantPayBody;
//import com.Ipaisa.Processor.InstantPayOutProcessor;
//import com.Ipaisa.reader.InstantPayItemReader;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//
//@Configuration
//
//public class BatchConfiguration {
//
//
//    
//
//    
//	@Bean
//	public ItemReader<InstantPayBody> flatFileItemReader(@Value("#{jobParameters['filePath']}") String filePath) {
//		  try {
//	            Resource resource = new UrlResource("file:" + filePath);
//	            FlatFileItemReader<InstantPayBody> itemReader = new FlatFileItemReader<>();
//	            itemReader.setLinesToSkip(1);
//	            itemReader.setResource(resource);
//
//	            DefaultLineMapper<InstantPayBody> lineMapper = new DefaultLineMapper<>();
//	            DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//	            lineTokenizer.setNames("payeraccountNumber", "name", "payeeaccountNumber", "bankIfsc", "transferMode",
//	                    "transferAmount", "latitude", "longitude", "remarks", "purpose");
//	            lineMapper.setLineTokenizer(lineTokenizer);
//	            lineMapper.setFieldSetMapper(new InstantPayFieldSetMapper());
//	            itemReader.setLineMapper(lineMapper);
//	            return itemReader;
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            throw new RuntimeException("Failed to read file", e);
//	        }
//
//	}
//	
//	  @Bean
//	    public ItemWriter<InstantPayBody> consoleItemWriter() {
//	        return items -> {
//	            for (InstantPayBody item : items) {
//	                System.out.println(item); // Assuming InstantPayBody has a proper toString() method
//	            }
//	        };
//	    }
//	
//	@Bean
//	public ItemProcessor<InstantPayBody, InstantPayBody> instantPayOutProcessor(){
//		return new InstantPayOutProcessor();
//	}
//	
//
//    @Bean
//    public Step firstStep(JobRepository jobRepository,PlatformTransactionManager transactionmanager) {
//        return new StepBuilder("step1",jobRepository)
//                .<InstantPayBody, InstantPayBody>chunk(3,transactionmanager)
//                .reader(flatFileItemReader (null))  // Ensure this method returns an ItemReader<InstantPayBody>
//               .processor(instantPayOutProcessor())
//               .writer(consoleItemWriter())
//               .build();
//    }
//
//    
//	@Bean
//    public Job firstJob(JobRepository jobRepository,PlatformTransactionManager transactionmanager) {
//        return new JobBuilder("job1",jobRepository)
//                .start(firstStep(jobRepository,transactionmanager))
//                .build();
//    }
//}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//package com.Ipaisa.batchConfig;
//
//import javax.sql.DataSource;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.partition.PartitionHandler;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.beans.factory.annotation.Value;
//import com.Ipaisa.Entitys.InstantPayBody;
//import com.Ipaisa.Processor.InstantPayOutProcessor;
//import com.Ipaisa.reader.InstantPayItemReader;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.batch.core.configuration.annotation.JobScope;
//import org.springframework.batch.core.configuration.annotation.StepScope;
////@Configuration
////public class BatchConfiguration {
////
////    @Bean
////    @StepScope
////    public FlatFileItemReader<InstantPayBody> flatFileItemReader(@Value("#{jobParameters['filePath']}") String filePath) {
////        try {
////            System.out.println("Initializing FlatFileItemReader with filePath: " + filePath);
////            Resource resource = new UrlResource("file:" + filePath);
////            FlatFileItemReader<InstantPayBody> itemReader = new FlatFileItemReader<>();
////            itemReader.setLinesToSkip(1);
////            itemReader.setResource(resource);
////
////            DefaultLineMapper<InstantPayBody> lineMapper = new DefaultLineMapper<>();
////            DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
////            lineTokenizer.setNames("payeraccountNumber", "name", "payeeaccountNumber", "bankIfsc", "transferMode",
////                    "transferAmount", "latitude", "longitude", "remarks", "purpose");
////            lineMapper.setLineTokenizer(lineTokenizer);
////            lineMapper.setFieldSetMapper(new InstantPayFieldSetMapper());
////            itemReader.setLineMapper(lineMapper);
////            return itemReader;
////        } catch (Exception e) {
////            e.printStackTrace();
////            throw new RuntimeException("Failed to read file", e);
////        }
////    }
////
////    @Bean
////    public ItemWriter<String> consoleItemWriter() {
////        System.out.println("Creating ItemWriter bean");
////        return items -> {
////            System.out.println("Writing items...");
////          System.out.println(items);
////        };
////    }
////
////    @Bean
////    public ItemProcessor<InstantPayBody, String> instantPayOutProcessor() {
////        return new InstantPayOutProcessor();
////    }
////
////    @Bean
////    public Step firstStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
////        System.out.println("Creating Step bean");
////        return new StepBuilder("step1", jobRepository)
////                .<InstantPayBody, String>chunk(10, transactionManager)
////                .reader(flatFileItemReader(null)) // This will be resolved at job execution time
////                .processor(instantPayOutProcessor())
////                .writer(consoleItemWriter())
////                .build();
////    }
////
////    @Bean
////    public Job firstJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
////        System.out.println("Creating Job bean");
////        return new JobBuilder("job1", jobRepository)
////                .start(firstStep(jobRepository, transactionManager))
////                .build();
////    }
////}
//import org.springframework.batch.core.explore.JobExplorer;
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//@Configuration
//public class BatchConfiguration {
//
//    @Bean
//    @StepScope
//    public FlatFileItemReader<InstantPayBody> flatFileItemReader(@Value("#{jobParameters['filePath']}") String filePath) {
//    	 try {
//           System.out.println("Initializing FlatFileItemReader with filePath: " + filePath);
//           Resource resource = new UrlResource("file:" + filePath);
//           FlatFileItemReader<InstantPayBody> itemReader = new FlatFileItemReader<>();
//           itemReader.setLinesToSkip(1);
//           itemReader.setResource(resource);
//
//           DefaultLineMapper<InstantPayBody> lineMapper = new DefaultLineMapper<>();
//           DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//           lineTokenizer.setNames("payeraccountNumber", "name", "payeeaccountNumber", "bankIfsc", "transferMode",
//                   "transferAmount", "latitude", "longitude", "remarks", "purpose");
//           lineMapper.setLineTokenizer(lineTokenizer);
//           lineMapper.setFieldSetMapper(new InstantPayFieldSetMapper());
//           itemReader.setLineMapper(lineMapper);
//           return itemReader;
//       } catch (Exception e) {
//           e.printStackTrace();
//           throw new RuntimeException("Failed to read file", e);
//       }
//   }
//
//    @Bean
//    public ItemWriter<String> consoleItemWriter() {
//        return items -> {
//            System.out.println("Writing items...");
//            System.out.println(items);
//        };
//    }
//
//    @Bean
//    public ItemProcessor<InstantPayBody, String> instantPayOutProcessor() {
//        return new InstantPayOutProcessor();
//    }
//
//    @Bean
//    public Step slaveStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("slaveStep", jobRepository)
//                .<InstantPayBody, String>chunk(10, transactionManager)
//                .reader(flatFileItemReader(null))
//                .processor(instantPayOutProcessor())
//                .writer(consoleItemWriter())
//                .build();
//    }
//
//    @Bean
//    public Step masterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, PartitionHandler partitionHandler) {
//        return new StepBuilder("masterStep", jobRepository)
//                .partitioner("slaveStep", new CustomPartitioner())
//                .partitionHandler(partitionHandler)
//                .build();
//    }
//
//    @Bean
//    public PartitionHandler partitionHandler(Step slaveStep, JobExplorer jobExplorer) {
//        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
//        partitionHandler.setTaskExecutor(taskExecutor());
//        partitionHandler.setStep(slaveStep);
//        partitionHandler.setGridSize(10);
//        partitionHandler.setJobExplorer(jobExplorer);
//        return partitionHandler;
//    }
//
//    @Bean
//    public TaskExecutor taskExecutor() {
//        return new SimpleAsyncTaskExecutor();
//    }
//
//    @Bean
//    public Job firstJob(JobRepository jobRepository, PlatformTransactionManager transactionManager, Step masterStep) {
//        return new JobBuilder("firstJob", jobRepository)
//                .start(masterStep)
//                .build();
//    }
//}
///////////////////////////////////////////////////////////second ////////////////////////////////////////////////////////////////////

//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.explore.JobExplorer;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.partition.PartitionHandler;
//import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ExecutionContext;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.retry.backoff.FixedBackOffPolicy;
//import org.springframework.retry.policy.SimpleRetryPolicy;
//import org.springframework.retry.support.RetryTemplate;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import com.Ipaisa.Entitys.InstantPayBody;
//import com.Ipaisa.Processor.InstantPayOutProcessor;
//import com.Ipaisa.Service.InstantpayBulkPayoutService;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class BatchConfiguration {
//
//    @Autowired
//    private InstantpayBulkPayoutService instantpayservice;
//
//    @Bean
//    @StepScope
//    public FlatFileItemReader<InstantPayBody> flatFileItemReader(@Value("#{jobParameters['filePath']}") String filePath) {
//        try {
//            System.out.println("Initializing FlatFileItemReader with filePath: " + filePath);
//            Resource resource = new UrlResource("file:" + filePath);
//            FlatFileItemReader<InstantPayBody> itemReader = new FlatFileItemReader<>();
//            itemReader.setLinesToSkip(1);
//            itemReader.setResource(resource);
//
//            DefaultLineMapper<InstantPayBody> lineMapper = new DefaultLineMapper<>();
//            DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//            lineTokenizer.setNames("payeraccountNumber", "name", "payeeaccountNumber", "bankIfsc", "transferMode",
//                    "transferAmount", "latitude", "longitude", "remarks", "purpose");
//            lineMapper.setLineTokenizer(lineTokenizer);
//            lineMapper.setFieldSetMapper(new InstantPayFieldSetMapper());
//            itemReader.setLineMapper(lineMapper);
//            return itemReader;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to read file", e);
//        }
//    }
//
//    @Bean
//    public ItemWriter<String> consoleItemWriter() {
//        return items -> {
//            System.out.println("Writing items...");
//            System.out.println(items);
//        };
//    }
//
//    @Bean
//    public ItemProcessor<InstantPayBody, String> instantPayOutProcessor() {
//        return new InstantPayOutProcessor();
//    }
//
//    @Bean
//    public Step slaveStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("slaveStep", jobRepository)
//                .<InstantPayBody, String>chunk(5, transactionManager)  
//                .reader(flatFileItemReader(null))
//                .processor(instantPayOutProcessor())
//                .writer(consoleItemWriter())
//                .taskExecutor(taskExecutor())
//                .build();
//    }
//
//    @Bean
//    public Step masterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, PartitionHandler partitionHandler) {
//        return new StepBuilder("masterStep", jobRepository)
//                .partitioner("slaveStep", new CustomPartitioner())
//                .partitionHandler(partitionHandler)
//                .build();
//    }
//
//    @Bean
//    public PartitionHandler partitionHandler(Step slaveStep) {
//        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
//        partitionHandler.setTaskExecutor(taskExecutor());
//        partitionHandler.setStep(slaveStep);
//        partitionHandler.setGridSize(10);
//        return partitionHandler;
//    }
//
//    @Bean
//    public TaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(10);  
//        taskExecutor.setMaxPoolSize(20);  
//        taskExecutor.setQueueCapacity(50);
//        taskExecutor.setThreadNamePrefix("Batch-Thread-");
//        taskExecutor.initialize();
//        return taskExecutor;
//    }
//    @Bean
//    public Job firstJob(JobRepository jobRepository, PlatformTransactionManager transactionManager, Step masterStep) {
//        return new JobBuilder("firstJob", jobRepository)
//                .start(masterStep)
//                .build();
//    }
//
//    @Bean
//    public RetryTemplate retryTemplate() {
//        RetryTemplate retryTemplate = new RetryTemplate();
//        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
//        retryPolicy.setMaxAttempts(3); // Maximum number of retry attempts
//        retryTemplate.setRetryPolicy(retryPolicy);
//        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
//        backOffPolicy.setBackOffPeriod(2000); // Delay between retries in milliseconds
//        retryTemplate.setBackOffPolicy(backOffPolicy);
//        return retryTemplate;
//    }
//
//   
//    }
//

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import com.Ipaisa.Entitys.InstantPayBody;
import com.Ipaisa.Processor.InstantPayOutProcessor;
import com.Ipaisa.Service.InstantpayBulkPayoutService;
import com.Ipaisa.Writers.InstantPayWriter;
import com.Ipaisa.listnere.MyItemProcessListener;
import com.Ipaisa.listnere.MyItemReadListener;
import com.Ipaisa.listnere.MyItemWriterListener;
import com.Ipaisa.listnere.MyJobExecutionListnere;
import com.Ipaisa.listnere.MySkipListener;
import com.Ipaisa.skipPolicy.MySkipPolicy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class BatchConfiguration {

    @Autowired
    private InstantpayBulkPayoutService instantpayservice;
    
    @Bean
    public MyJobExecutionListnere myJobExecutionListnere() {
    	return new MyJobExecutionListnere();
    }
    
    @Bean 
    public MyItemReadListener myItemReadListener() {
    	return new MyItemReadListener();
    } 
    
    @Bean
    public MyItemProcessListener myItemProcessListener() {
    	return new MyItemProcessListener();
    }
    
    
    
    @Bean 
    public MyItemWriterListener myItemWriterListener() {
    	return new MyItemWriterListener();
    } 
    
    @Bean InstantPayWriter instantPayWriter() {
    	return new InstantPayWriter();
    }
    
    

    @Bean
    @StepScope
    public FlatFileItemReader<InstantPayBody> flatFileItemReader(@Value("#{jobParameters['filePath']}") String filePath) {
        try {
            System.out.println("Initializing FlatFileItemReader with filePath: " + filePath);
            Resource resource = new UrlResource("file:" + filePath);
            FlatFileItemReader<InstantPayBody> itemReader = new FlatFileItemReader<>();
            itemReader.setLinesToSkip(1);
            itemReader.setResource(resource);
            itemReader.setRecordSeparatorPolicy(new NonBlankLineRecordSeparatorPolicy());


            DefaultLineMapper<InstantPayBody> lineMapper = new DefaultLineMapper<>();
            CustomLineTokenizer lineTokenizer = new CustomLineTokenizer();
           
//            lineTokenizer.setNames("payeraccountNumber", "name", "payeeaccountNumber", "bankIfsc", "transferMode",
//                    "transferAmount", "latitude", "longitude", "remarks", "purpose");
//            lineTokenizer.setStrict(false); 
            lineMapper.setLineTokenizer(lineTokenizer);
            lineMapper.setFieldSetMapper(new InstantPayFieldSetMapper());
            itemReader.setLineMapper(lineMapper);
            return itemReader;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read file", e);
        }
    }

//    @Bean
//    public ItemWriter<String> consoleItemWriter() {
//    	
//        return items -> {
//            System.out.println("Writing items...");
//            
//            System.out.println(items);
//        };
//    }

    @Bean
    public ItemProcessor<InstantPayBody, ResponseDTO> instantPayOutProcessor() {
        return new InstantPayOutProcessor();
    }
    
    @Bean
    public MySkipPolicy mySkipPolicy() {
    	return new MySkipPolicy();
    }
    
    
    @Bean
    public MySkipListener mySkipListener() {
    	return new MySkipListener();
    }
    
    @Bean
    public MyJobExecutionListener myJobExecutionListener(MySkipListener skipListener, ItemProcessor<InstantPayBody, ResponseDTO> processor) {
        return new MyJobExecutionListener(skipListener, processor);
    }
    

  @Bean
  public Step firstStep(JobRepository jobRepository,PlatformTransactionManager transactionmanager) {
	  
      return new StepBuilder("step1",jobRepository)
              .<InstantPayBody, ResponseDTO>chunk(3,transactionmanager)
              .reader(flatFileItemReader (null))  // Ensure this method returns an ItemReader<InstantPayBody>
             .processor(instantPayOutProcessor())
             .writer(instantPayWriter())
             .listener(myItemReadListener())
             //.listener(myItemProcessListener())
             //.listener(myItemWriterListener())
             .faultTolerant()
             .skip(IncorrectTokenCountException.class)
             .skipPolicy(new AlwaysSkipItemSkipPolicy())
            
             .listener(mySkipListener())
          //   .skipPolicy(mySkipPolicy())
             .build();
  }

  
	@Bean
  public Job firstJob(JobRepository jobRepository,PlatformTransactionManager transactionmanager) {
      return new JobBuilder("job1",jobRepository)
    		  .listener(myJobExecutionListener(mySkipListener(), instantPayOutProcessor()))
              .start(firstStep(jobRepository,transactionmanager))
              
              .build();
  }
	@Bean
	public RetryTemplate retryTemplate() {
	    RetryTemplate retryTemplate = new RetryTemplate();

	    // Retry 3 times except for NonRetryableException
	    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(3,
	        Collections.singletonMap(NonRetryableException.class, false), true);
	    retryTemplate.setRetryPolicy(retryPolicy);

	    // 2 seconds delay between retries
	    FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
	    backOffPolicy.setBackOffPeriod(2000); // Delay between retries in milliseconds
	    retryTemplate.setBackOffPolicy(backOffPolicy);

	    return retryTemplate;
	}
   
    }








