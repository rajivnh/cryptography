package com.hcl.store;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Request {
	private String text;
	
	private String cypherText;
	
	private String hashedText;
	
	public Request() {
		
	}
}
