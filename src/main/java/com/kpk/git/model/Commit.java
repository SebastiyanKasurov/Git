package com.kpk.git.model;

import java.time.LocalDateTime;

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
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
}
