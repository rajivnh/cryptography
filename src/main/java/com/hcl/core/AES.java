package com.hcl.core;

public interface AES {
	public byte[] encrypt(byte[] plainText);
	
	public byte[] decrypt(byte[] cipherText);
}
