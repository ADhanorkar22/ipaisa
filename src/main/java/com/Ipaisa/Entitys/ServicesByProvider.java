package com.Ipaisa.Entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ServicesByProvider {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="bank_charges")
	private Double bankChargesl;
	
	
	  @ManyToOne
	    @JoinColumn(name = "provider_id", nullable = false)
	    private Providers provider;

	    @ManyToOne
	    @JoinColumn(name = "service_id", nullable = false)
	    private Services service;


	    
	    public ServicesByProvider() {
			// TODO Auto-generated constructor stub
		}



		public ServicesByProvider(Long id, Double bankChargesl, Providers provider, Services service) {
			super();
			this.id = id;
			this.bankChargesl = bankChargesl;
			this.provider = provider;
			this.service = service;
		}
		public ServicesByProvider(Double bankChargesl, Providers provider, Services service) {
			super();
			this.bankChargesl = bankChargesl;
			this.provider = provider;
			this.service = service;
		}



		public Long getId() {
			return id;
		}



		public void setId(Long id) {
			this.id = id;
		}



		public Double getBankChargesl() {
			return bankChargesl;
		}



		public void setBankChargesl(Double bankChargesl) {
			this.bankChargesl = bankChargesl;
		}



		public Providers getProvider() {
			return provider;
		}



		public void setProvider(Providers provider) {
			this.provider = provider;
		}



		public Services getService() {
			return service;
		}



		public void setService(Services service) {
			this.service = service;
		}



		@Override
		public String toString() {
			return "ServicesByProvider [id=" + id + ", bankChargesl=" + bankChargesl + ", provider=" + provider
					+ ", service=" + service + "]";
		}
		
		
	    
	    
}
		