package com.kpk.git.util.exceptions;

public class NonExistentFileException extends RuntimeException {
	public NonExistentFileException(String msg) {
		super(msg);
	}
}
