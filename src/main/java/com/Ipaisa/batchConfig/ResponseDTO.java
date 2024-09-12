package com.Ipaisa.batchConfig;

public class ResponseDTO {
	
	private String response;
	private String transferMode;
	
	public ResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	public ResponseDTO(String response, String transferMode) {
		super();
		this.response = response;
		this.transferMode = transferMode;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getTransferMode() {
		return transferMode;
	}

	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}

	@Override
	public String toString() {
		return "ResponseDTO [response=" + response + ", transferMode=" + transferMode + "]";
	}

	
	
}