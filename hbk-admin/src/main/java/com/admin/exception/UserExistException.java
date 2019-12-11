package com.admin.exception;

public class UserExistException extends RuntimeException {
	private static final long serialVersionUID = -3048949646987697875L;

	public UserExistException() {
	}

	public UserExistException(String message) {
		super(message);
	}

	public UserExistException(Throwable cause) {
		super(cause);
	}

	public UserExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
