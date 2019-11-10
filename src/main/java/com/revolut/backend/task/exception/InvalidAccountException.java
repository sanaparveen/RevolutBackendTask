package com.revolut.backend.task.exception;

import com.revolut.backend.task.model.RevolutAPIError;

/**
 * @author sanaparveen
 *
 */
public class InvalidAccountException extends RevolutAPIException {

	private static final long serialVersionUID = 8318141911002481263L;

	public InvalidAccountException(RevolutAPIError error) {
		super(error);
	}
}
