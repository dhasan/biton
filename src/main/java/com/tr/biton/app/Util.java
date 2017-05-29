package com.tr.biton.app;

import java.nio.ByteBuffer;

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
}
