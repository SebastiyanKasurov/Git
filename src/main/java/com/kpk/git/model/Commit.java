package com.kpk.git.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Commit {
	private String message;
	private LocalDateTime timeCreated;
	private String hash;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public LocalDateTime getTimeCreated() {
		return timeCreated;
	}
	
	public void setTimeCreated(LocalDateTime timeCreated) {
		this.timeCreated = timeCreated;
	}
	
	public String getHash() {
		return hexDigest(this.getTimeCreatedAfterFormat() + message);
	}
	
	private String getTimeCreatedAfterFormat() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E LLL dd kk:mm yyyy");
		return formatter.format(timeCreated);
	}
	
	private String hexDigest(String input) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("No such algorithm.", e);
		}
		byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
		return convertBytesToHex(bytes);
	}
	
	private String convertBytesToHex(byte[] bytes) {
		StringBuilder hex = new StringBuilder();
		for (byte current : bytes) {
			hex.append(String.format("%02x", current));
		}
		
		return hex.toString();
	}
}
