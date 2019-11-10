/**
 * 
 */
package com.revolut.backend.task.exception;

import java.io.Serializable;

import com.revolut.backend.task.model.RevolutAPIError;

/**
 * @author sanaparveen
 *
 */
public class RevolutAPIException extends Exception implements Serializable {

	private static final long serialVersionUID = -3831185523977231850L;

	private RevolutAPIError apiError;

	public RevolutAPIException(RevolutAPIError error) {
		super();
	}

	public RevolutAPIError getApiError() {
		return apiError;
	}

	public void setApiError(RevolutAPIError apiError) {
		this.apiError = apiError;
	}
}
