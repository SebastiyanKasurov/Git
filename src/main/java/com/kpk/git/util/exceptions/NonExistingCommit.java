package com.kpk.git.util.exceptions;

public class NonExistingCommit extends AbstractClientException {
	public NonExistingCommit(String msg) {
		super(msg);
	}
}
