package com.jeff.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest {
	
	public static String digest(String password,String algName){
		String result = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(algName);
			byte[] content = digest.digest(password.getBytes("UTF-8"));
			return Parse.parseBytesToHexStr(content);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
