package com.Ipaisa.batchConfig;

import org.springframework.batch.item.file.separator.RecordSeparatorPolicy;

public class NonBlankLineRecordSeparatorPolicy implements RecordSeparatorPolicy {


    @Override
    public boolean isEndOfRecord(String line) {
    	  return line != null && !line.trim().isEmpty();
    }

    @Override
    public String postProcess(String record) {
        return record;
    }

    @Override
    public String preProcess(String record) {
        return (record == null || record.trim().isEmpty()) ? null : record;
    }
}