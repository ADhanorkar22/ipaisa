package com.Ipaisa.Entitys;

import java.util.List;

public class KYCDTO {
	
	List<String>  uploadedFiles ;
	List<String> remainingFiles;
	
	public KYCDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public KYCDTO(List<String> uploadedFiles, List<String> remainingFiles) {
		super();
		this.uploadedFiles = uploadedFiles;
		this.remainingFiles = remainingFiles;
	}

	public List<String> getUploadedFiles() {
		return uploadedFiles;
	}

	public void setUploadedFiles(List<String> uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	public List<String> getRemainingFiles() {
		return remainingFiles;
	}

	public void setRemainingFiles(List<String> remainingFiles) {
		this.remainingFiles = remainingFiles;
	}
	
	
	
	
	
	

}
