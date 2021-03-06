/**
 * 
 */
package com.revolut.backend.task.model;

import java.io.Serializable;

/**
 * @author sanaparveen
 *
 */
public class RevolutAPIError implements Serializable {

	private static final long serialVersionUID = 6110518389126487837L;
	private String code;
	private String message;
	private String field;
	private int status;
	private String error;

	public RevolutAPIError(String code, String message, int status) {
		this.code = code;
		this.message = message;
		this.status = status;
	}

	public RevolutAPIError(String code, String message, String field, int status) {
		this(code, message, status);
		this.message = message;
	}

	public RevolutAPIError(String code, String message, String field, int status, String error) {
		this(code, message, field, status);
		this.error = error;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "RevolutAPIError [code=" + code + ", message=" + message + ", field=" + field + ", status=" + status
				+ ", error=" + error + "]";
	}

	
}
