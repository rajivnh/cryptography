package com.hcl.web;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.core.AES;
import com.hcl.core.Becrypt;
import com.hcl.store.Request;
import com.hcl.store.Response;

@Component
@RestController
public class Crypto<T> {
	@Autowired
	private AES aes;
	
	@Autowired
	private Becrypt becrypt;
	
	public Crypto() {
		
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/api/encrypt")
	public T encrypt(@RequestBody Request request) {
		Response response = new Response();
		
		try {
			response.setResults(1);
			
			response.setMessageId("CRYENC0001");
			response.setMessage("ENCRYPTED SUCCESSFULLY");
			
			response.setAnswer(Base64.getEncoder().encodeToString(aes.encrypt(request.getText().getBytes())));
			
			return (T) new ResponseEntity<>(response, HttpStatus.OK);
		} catch(Exception e) {
			
		}
		
		response.setResults(0);
		
		response.setMessageId("CRYENC0002");
		response.setMessage("ENCRYPTION FAILED");
		
		return (T) new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/api/decrypt")
	public T decrypt(@RequestBody Request request) {
		Response response = new Response();
		
		try {
			response.setResults(1);
			
			response.setMessageId("CRYENC0003");
			response.setMessage("DECRYPTED SUCCESSFULLY");
			
			response.setAnswer(new String(aes.decrypt(Base64.getDecoder().decode(request.getCypherText()))));
			
			return (T) new ResponseEntity<>(response, HttpStatus.OK);
		} catch(Exception e) {
			
		}
		
		response.setResults(0);
		
		response.setMessageId("CRYDEC0004");
		response.setMessage("DECRYPTION FAILED");
		
		return (T) new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/api/hashpw")
	public T hashpw(@RequestBody Request request) {
		Response response = new Response();
		
		try {
			response.setResults(1);
			response.setMessageId("CRYENC0005");
			response.setMessage("HASHED SUCCESSFULLY");
			
			response.setAnswer(new String(becrypt.hashpw(request.getText())));
			
			return (T) new ResponseEntity<>(response, HttpStatus.OK);
		} catch(Exception e) {
			
		}
		
		response.setResults(0);
		
		response.setMessageId("CRYHPW0006");
		response.setMessage("HASHING FAILED");
		
		return (T) new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/api/checkpw")
	public T checkpw(@RequestBody Request request) {
		Response response = new Response();
		
		try {
			boolean hashStatus = becrypt.isValid(request.getText(), request.getHashedText());
			
			if(hashStatus) {
				response.setResults(1);
				
				response.setMessageId("CRYCPW0007");
				response.setMessage("PASSWORD MATCHED");
				response.setAnswer("VALID");
				
				return (T) new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setResults(0);
				
				response.setMessageId("CRYCPW0008");
				response.setMessage("PASSWORD DOES NOT MATCH");	
				response.setAnswer("INVALID");
				
				return (T) new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch(Exception e) {
			
		}
		
		response.setResults(0);
		response.setMessageId("CRYENC0009");
		response.setMessage("ERROR HASHING");
		
		return (T) new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	public static void main(String[] args) throws Exception {
		String s = "";
		
		System.out.println(Optional.ofNullable(s).filter(ss -> !ss.isEmpty()).isEmpty());
		
		System.out.println(check(String.valueOf("aaa"), String.valueOf("aaa")));
	} 	
	
	private static boolean check(String s1, String s2) {
		return s1 == s2 ? true : false;
	}
}
