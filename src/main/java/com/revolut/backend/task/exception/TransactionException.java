package com.revolut.backend.task.exception;

public class TransactionException extends Exception {

	private static final long serialVersionUID = -5512649855720819515L;

	public TransactionException(String msg) {
		super(msg);
	}

}
