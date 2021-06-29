package com.hcl.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BcryptImpl implements Becrypt {
	@Value("${bcrypt.salt}")
	private String becryptSalt;
	
	public BcryptImpl() {
		
	}
	
	@Override
	public String hashpw(String pw) {
		return BCrypt.hashpw(pw, becryptSalt);
	}
	
	@Override
	public boolean isValid(String pw, String hashedPw) {
		return BCrypt.checkpw(pw, hashedPw);
	}
}
