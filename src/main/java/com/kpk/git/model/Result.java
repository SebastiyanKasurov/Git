package com.kpk.git.model;

public class Result {
	private boolean success;
	private String message;
	
	public Result(String message, boolean result) {
		this.success = result;
		this.message = message;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public String getMessage() {
		return message;
	}
}