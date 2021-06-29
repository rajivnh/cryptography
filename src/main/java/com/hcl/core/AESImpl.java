package com.hcl.core;

import java.nio.ByteBuffer;
import java.security.SecureRandom;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AESImpl implements AES {
	@Value("${aes.key}")
	private String aesKey;
	
	private Cipher encryptCipher;
	private Cipher decryptCipher;
	
	private byte[] key;
	private int keyLength;
	
	private SecretKeySpec keySpec;
	private SecureRandom random;

	public AESImpl() {

	}
	
	@PostConstruct
	public void init() {
		try {
			this.key = aesKey.getBytes();
			this.keyLength = this.key.length * 8; 
			
			this.keySpec = new SecretKeySpec(this.key, "AES");
			
			this.encryptCipher = Cipher.getInstance("AES/GCM/NoPadding");
			this.decryptCipher = Cipher.getInstance("AES/GCM/NoPadding");
			
			this.random = SecureRandom.getInstance("SHA1PRNG");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public byte[] encrypt(byte[] plaintext) {
		try {
			byte[] iv = new byte[12];
			
			random.nextBytes(iv);
			encryptCipher.init(Cipher.ENCRYPT_MODE, keySpec, new GCMParameterSpec(keyLength, iv));
			
			byte[] encrypted = encryptCipher.doFinal(plaintext);
			
			ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + encrypted.length);
			
			byteBuffer.put(iv);
			byteBuffer.put(encrypted);
			
			return byteBuffer.array(); 
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}

	@Override
	public byte[] decrypt(byte[] ciphertext) {
		try {
			ByteBuffer byteBuffer = ByteBuffer.wrap(ciphertext);
			
			byte[] iv = new byte[12];
			byteBuffer.get(iv);
			
			byte[] encrypted = new byte[byteBuffer.remaining()];
			byteBuffer.get(encrypted);
			
			decryptCipher.init(Cipher.DECRYPT_MODE, keySpec, new GCMParameterSpec(keyLength, iv));
			
			return decryptCipher.doFinal(encrypted); 
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
}