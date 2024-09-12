package com.Ipaisa.listnere;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;

import com.Ipaisa.Entitys.InstantPayBody;
import com.Ipaisa.batchConfig.ResponseDTO;

public class MySkipListener implements SkipListener<InstantPayBody, ResponseDTO> {
	
	  public static final List<String> badRecords = new ArrayList<>();

	@Override
	public void onSkipInRead(Throwable t) {
		System.out.println("in skippppppppppppppppppppppppp");
		  if (t instanceof FlatFileParseException) {
			  if(((FlatFileParseException) t).getInput()!=null&&((FlatFileParseException) t).getInput()!="")
	            badRecords.add(((FlatFileParseException) t).getInput());
	        }
	}

	@Override
	public void onSkipInWrite(ResponseDTO item, Throwable t) {
		// TODO Auto-generated method stub
		SkipListener.super.onSkipInWrite(item, t);
	}

	@Override
	public void onSkipInProcess(InstantPayBody item, Throwable t) {
		System.out.println("in unnecessery onSkipInProcess");
		  badRecords.add(item.toString());
	}
	

	
	 public List<String> getBadRecords() {
	        return badRecords;
	    }

}
