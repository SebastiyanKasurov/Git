package com.kpk.git.util.exceptions;

public class ExistingBranchException extends RuntimeException {
	
	public ExistingBranchException(String msg) {
		super(msg);
	}
}
