package com.jeff.common.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

public class RSA {
	
	
	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;
	private Cipher c;
	public RSA() throws Exception{
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		publicKey = (RSAPublicKey) keyPair.getPublic();
		privateKey = (RSAPrivateKey)keyPair.getPrivate();
	}
	public byte[] usePublicKeyToEncrypt(byte[] content) throws Exception{
		c = Cipher.getInstance("RSA");
		c.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] result = c.doFinal(content);
		return result;
	}
	
	public byte[] userPrivateKeyToDecrypt(byte[] content) throws Exception{
		c = Cipher.getInstance("RSA");
		c.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] result = c.doFinal(content);
		return result;
	}
	
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}
	
}
