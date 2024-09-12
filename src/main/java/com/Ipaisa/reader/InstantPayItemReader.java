package com.Ipaisa.reader;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.Ipaisa.Entitys.InstantPayBody;

public class InstantPayItemReader implements ItemReader<InstantPayBody> {
	
	private Iterator<InstantPayBody> instantPayIterator;
	
	public InstantPayItemReader(List<InstantPayBody> paybody) {
		this.instantPayIterator=paybody.iterator();
	}

	@Override
	public InstantPayBody read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		return instantPayIterator.hasNext()?this.instantPayIterator.next():null;
	}

}
