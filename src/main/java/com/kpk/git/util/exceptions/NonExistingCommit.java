package com.kpk.git.util.exceptions;

public class NonExistingCommit extends RuntimeException {
	public NonExistingCommit(String msg) {
		super(msg);
	}
}
