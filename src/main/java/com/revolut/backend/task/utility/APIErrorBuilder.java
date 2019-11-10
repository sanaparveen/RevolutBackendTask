package com.revolut.backend.task.utility;

import com.revolut.backend.task.model.RevolutAPIError;

public class APIErrorBuilder {

	public static RevolutAPIError buildAPIError(String code, String message, int status) {
		return new RevolutAPIError(code, message, status);
	}

	public static RevolutAPIError buildAPIError(String code, String message, String field, int status) {
		return new RevolutAPIError(code, message, field, status);
	}
}
