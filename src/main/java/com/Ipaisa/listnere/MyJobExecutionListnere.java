package com.Ipaisa.listnere;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

public class MyJobExecutionListnere {

	@BeforeJob
	public void beforeJob(JobExecution jobExecution) {
		System.out.println(jobExecution.getJobInstance().getJobName());
		System.out.println(jobExecution.getJobParameters());
		System.out.println(jobExecution.getStartTime());
		System.out.println(jobExecution.getStatus());
		
		
		
		//JobExecutionListener.super.beforeJob(jobExecution);
	}

	@AfterJob
	public void afterJob(JobExecution jobExecution) {
		System.out.println(jobExecution.getEndTime());
		
	//	JobExecutionListener.super.afterJob(jobExecution);
	}

}
