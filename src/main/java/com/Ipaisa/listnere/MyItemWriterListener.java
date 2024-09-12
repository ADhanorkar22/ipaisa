package com.Ipaisa.listnere;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;

public class MyItemWriterListener implements ItemWriteListener<String> {

	@Override
	public void beforeWrite(Chunk<? extends String> items) {
		System.out.println(items);
	}

	@Override
	public void afterWrite(Chunk<? extends String> items) {
		System.out.println(items);
	}

	@Override
	public void onWriteError(Exception exception, Chunk<? extends String> items) {
		System.out.println(items);
	}
	

}
