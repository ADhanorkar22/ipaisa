package com.Ipaisa.DeepLink;

public class GenratedKey {
	  private boolean status;
	    private String key;
	    
	    public GenratedKey() {
			// TODO Auto-generated constructor stub
		}

		public GenratedKey(boolean status, String key) {
			super();
			this.status = status;
			this.key = key;
		}

		public boolean isStatus() {
			return status;
		}

		public void setStatus(boolean status) {
			this.status = status;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
	    
		
		
}
