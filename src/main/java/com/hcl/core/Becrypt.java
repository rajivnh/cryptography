package com.hcl.core;

public interface Becrypt {
	public String hashpw(String pw);
	
	public boolean isValid(String pw, String hashedPw);
}
