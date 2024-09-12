package com.Ipaisa.listnere;

import org.springframework.batch.core.ItemReadListener;

import com.Ipaisa.Entitys.InstantPayBody;

public class MyItemReadListener implements ItemReadListener<InstantPayBody> {

	@Override
	public void beforeRead() {
		// TODO Auto-generated method stub
		ItemReadListener.super.beforeRead();
	}

	@Override
	public void afterRead(InstantPayBody item) {
		// TODO Auto-generated method stub
		ItemReadListener.super.afterRead(item);
	}

	@Override
	public void onReadError(Exception ex) {
		// TODO Auto-generated method stub
		ItemReadListener.super.onReadError(ex);
	}
	

}
