package com.Ipaisa.DeepLink;

import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
@Service
public class DataCompressionService {
	 // Compress the data
    public byte[] compress(String data) throws Exception {
        byte[] input = data.getBytes("UTF-8");
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        deflater.finish();

        byte[] buffer = new byte[1024];
        int compressedDataLength = deflater.deflate(buffer);
        deflater.end();

        byte[] output = new byte[compressedDataLength];
        System.arraycopy(buffer, 0, output, 0, compressedDataLength);
        return output;
    }

    // Encode the compressed data
    public String encodeCompressedData(byte[] compressedData) {
        return Base64.getEncoder().encodeToString(compressedData);
    }

    // Decompress the data
    public String decompress(byte[] compressedData) throws Exception {
        Inflater inflater = new Inflater();
        inflater.setInput(compressedData);
        byte[] buffer = new byte[1024];
        int decompressedDataLength = inflater.inflate(buffer);
        inflater.end();

        return new String(buffer, 0, decompressedDataLength, "UTF-8");
    }

    // Encode and compress data
    public String compressAndEncode(String data) throws Exception {
        byte[] compressedData = compress(data);
        return encodeCompressedData(compressedData);
    }

    // Decode and decompress data
    public String decodeAndDecompress(String encodedData) throws Exception {
        byte[] decodedData = Base64.getDecoder().decode(encodedData);
        return decompress(decodedData);
    }


	
}
