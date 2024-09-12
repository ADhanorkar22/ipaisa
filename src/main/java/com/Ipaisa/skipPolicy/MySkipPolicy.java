package com.Ipaisa.skipPolicy;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;

public class MySkipPolicy implements SkipPolicy {

	@Override
	public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
		System.out.println("skip count :"+skipCount);
		if(t instanceof FlatFileParseException) {
			String line =((FlatFileParseException) t).getInput();
			String[] lineArr=line.split(",");
			if(lineArr.length>10&&lineArr.length<10) {
			return true;
			
		}
		
	}
		return false;
	}
}


