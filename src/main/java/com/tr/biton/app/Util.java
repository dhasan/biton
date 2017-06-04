package com.tr.biton.app;

import java.nio.ByteBuffer;
import java.util.Random;

public class Util {
	static public byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	
	static public String byteArrayToHex(byte[] a) {
		   StringBuilder sb = new StringBuilder(a.length * 2);
		   for(byte b: a)
		      sb.append(String.format("%02x", b & 0xff));
		   return sb.toString();
	}
	
	static public int byteArrayToInt(byte[] bytes) {
	     return ByteBuffer.wrap(bytes).getInt();
	}
	
	static public byte[] intToByteArray(int value) {
	     return  ByteBuffer.allocate(4).putInt(value).array();
	}
	
	static public long byteArrayToLong(byte[] bytes) {
	     return ByteBuffer.wrap(bytes).getLong();
	}
	
	static public byte[] longToByteArray(long value) {
	     return  ByteBuffer.allocate(8).putLong(value).array();
	}
	
	static public String getSaltString(int size) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < size) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
