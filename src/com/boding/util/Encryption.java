package com.boding.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.boding.constants.Constants;

public class Encryption {
	public static String getMD5(String string) throws NoSuchAlgorithmException {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}
	
	public static String getSign(String ...args) throws NoSuchAlgorithmException{
		String sign = null;
		StringBuilder sb = new StringBuilder();
		for(String s : args){
			sb.append(s);
		}
		sb.append(getMD5(Constants.BODINGKEY).toUpperCase());
		sign = getMD5(sb.toString()).toUpperCase();
		return sign;
	}
}
