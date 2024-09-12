package com.Ipaisa.batchConfig;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.core.io.Resource;

import com.Ipaisa.Entitys.InstantPayBody;

import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.IncorrectTokenCountException;
import org.springframework.batch.item.file.transform.LineTokenizer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomItemReader extends FlatFileItemReader<InstantPayBody> {

    private BufferedWriter rejectedWriter;
    private LineTokenizer tokenizer;
    private int expectedFieldCount;

    public CustomItemReader(Resource resource, String rejectedFilePath, LineTokenizer tokenizer, int expectedFieldCount) throws IOException {
        setResource(resource);
        this.tokenizer = tokenizer;
        this.expectedFieldCount = expectedFieldCount;
        
        Path rejectedFile = Paths.get(rejectedFilePath);
        Files.createDirectories(rejectedFile.getParent());
        rejectedWriter = Files.newBufferedWriter(rejectedFile);

        // Use a custom record separator policy to skip empty lines
        setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy() {
            @Override
            public boolean isEndOfRecord(String line) {
                return super.isEndOfRecord(line.trim());
            }
        });
    }

    @Override
    public InstantPayBody read() throws Exception {
        try {
            InstantPayBody item = super.read();
            if (item != null) {
                String[] fields = tokenizer.tokenize(item.toString()).getValues();
                if (fields.length != expectedFieldCount) {
                    throw new IncorrectTokenCountException(expectedFieldCount, fields.length);
                }
            }
            return item;
        } catch (FlatFileParseException e) {
            rejectedWriter.write(e.getInput());
            rejectedWriter.newLine();
            return null;
        } catch (IncorrectTokenCountException e) {
            // Log the incorrect record and skip it
            rejectedWriter.write(e.getInput());
            rejectedWriter.newLine();
            return null;
        }
    }

    @Override
    public void close() {
        super.close();
        if (rejectedWriter != null) {
            try {
				rejectedWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
