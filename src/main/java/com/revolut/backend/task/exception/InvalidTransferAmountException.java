package com.revolut.backend.task.exception;

import com.revolut.backend.task.model.RevolutAPIError;

public class InvalidTransferAmountException extends RevolutAPIException {

	private static final long serialVersionUID = -6075891265985838886L;

	public InvalidTransferAmountException(RevolutAPIError error) {
		super(error);
	}
}
