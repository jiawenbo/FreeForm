package com.jeff.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {
	private String algName;
	private int keySize;
	public Encrypt(String algName, int keySize){
		this.algName = algName;
		this.keySize = keySize;
	}
	
	public byte[] crypt(String content, String password){
		byte[] result = null;
		try {
			//创建密钥生成器（描述密钥生成的各项指标）
			KeyGenerator keyGen = KeyGenerator.getInstance(algName);
			//密钥生成器初始化
			keyGen.init(keySize, new SecureRandom(password.getBytes()));
			//生成一个新密钥
			SecretKey key = keyGen.generateKey();
			//用密钥生成密钥描述规格说明
			SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), algName);
			
			//密钥工厂（生成密钥）
			Cipher c = Cipher.getInstance(algName);
			//根据密钥说明书初始化
			c.init(Cipher.ENCRYPT_MODE, keySpec);
			//输出加密后的内容
			result = c.doFinal(content.getBytes("utf-8"));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	public byte[] decrypt(byte[] content, String password){
		byte[] result = null;
		// the same with the crypt function
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance(algName);
			keyGen.init(keySize, new SecureRandom(password.getBytes()));
			SecretKey key = keyGen.generateKey();
			SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), algName);
			
			Cipher c = Cipher.getInstance(algName);
			c.init(Cipher.DECRYPT_MODE, keySpec);
			result = c.doFinal(content);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
}
