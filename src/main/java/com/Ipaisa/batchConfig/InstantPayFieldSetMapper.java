package com.Ipaisa.batchConfig;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.Ipaisa.Entitys.InstantPayBody;

public class InstantPayFieldSetMapper implements FieldSetMapper<InstantPayBody> {
	

   

	@Override
	public InstantPayBody mapFieldSet(FieldSet fieldSet) throws BindException {
		  if (fieldSet == null) {
	            return null;
	        }

		 return new InstantPayBody(
		          
		            fieldSet.readString("name"),
		            fieldSet.readString("payeeaccountNumber"),
		            fieldSet.readString("bankIfsc"),
		            fieldSet.readString("transferMode"),
		            fieldSet.readString("transferAmount")
		           
		        );
		
	}

}
