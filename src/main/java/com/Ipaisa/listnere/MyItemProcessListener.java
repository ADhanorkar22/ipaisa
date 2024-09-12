package com.Ipaisa.listnere;

import org.springframework.batch.core.ItemProcessListener;

import com.Ipaisa.Entitys.InstantPayBody;

public class MyItemProcessListener implements ItemProcessListener<InstantPayBody, String> {

	@Override
	public void beforeProcess(InstantPayBody item) {
		System.out.println(item.getPayeeaccountNumber());
	}

	@Override
	public void afterProcess(InstantPayBody item, String result) {
		System.out.println(item.getPayeeaccountNumber());
	}

	@Override
	public void onProcessError(InstantPayBody item, Exception e) {
		System.out.println(item.getPayeeaccountNumber());
	}
	
	

}
