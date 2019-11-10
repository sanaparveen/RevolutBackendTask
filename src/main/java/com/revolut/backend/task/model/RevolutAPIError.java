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

	public RevolutAPIError(String code, String message, int status) {
		this.code = code;
		this.message = message;
		this.status = status;
	}

	public RevolutAPIError(String code, String message, String field, int status) {
		this(code, message, status);
		this.message = message;
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
}
