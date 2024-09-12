package com.Ipaisa.batchConfig;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;

public class CustomLineTokenizer extends DelimitedLineTokenizer {

    public CustomLineTokenizer() {
        // Set the delimiter and names here
        super.setDelimiter(",");
        super.setNames(new String[] {"name", "payeeaccountNumber", "bankIfsc", "transferMode",
                "transferAmount"});
    }

    @Override
    public FieldSet tokenize(String line) {
        String[] tokens = line.split(getDelimiter());
        if (tokens.length != getNames().length) {
            throw new IncorrectTokenCountException(getNames().length, tokens.length, line);
        }
        return super.tokenize(line);
    }

    private String getDelimiter() {
        return ",";
    }

    private String[] getNames() {
        return new String[] {"name", "payeeaccountNumber", "bankIfsc", "transferMode",
                "transferAmount"};
    }
}