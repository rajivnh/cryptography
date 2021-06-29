package com.hcl.store;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Response {
	private int results;
	
	private String messageId;
	
	private String message;
	
	private String answer;
	
	public Response() {
		
	}
}
