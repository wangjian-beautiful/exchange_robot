package com.bjs;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtils {
	
	public static byte[] compress(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes("UTF-8"));
            gzip.close();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
	
	public static void main(String[] args) {
		System.out.println(compress("1111"));
	}

}