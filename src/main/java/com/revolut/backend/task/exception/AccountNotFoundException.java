/**
 * 
 */
package com.revolut.backend.task.exception;

import com.revolut.backend.task.model.RevolutAPIError;

/**
 * @author sanaparveen
 *
 */
public class AccountNotFoundException extends RevolutAPIException {

	private static final long serialVersionUID = -4266973418743202879L;

	public AccountNotFoundException(RevolutAPIError error) {
		super(error);
	}
}
