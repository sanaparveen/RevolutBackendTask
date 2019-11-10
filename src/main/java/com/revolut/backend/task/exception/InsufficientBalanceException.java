/**
 * 
 */
package com.revolut.backend.task.exception;

import com.revolut.backend.task.model.RevolutAPIError;

/**
 * @author sanaparveen
 *
 */
public class InsufficientBalanceException extends RevolutAPIException {

	private static final long serialVersionUID = 128909052502796749L;

	public InsufficientBalanceException(RevolutAPIError apiError) {
		super(apiError);
	}

}
