package com.kpk.git.util.exceptions;

public class NonExistentFileException extends AbstractClientException {
	public NonExistentFileException(String msg) {
		super(msg);
	}
}
