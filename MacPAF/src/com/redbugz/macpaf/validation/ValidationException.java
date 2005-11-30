package com.redbugz.macpaf.validation;

public class ValidationException extends RuntimeException {
	String errorDetail = "";
	Object invalidObject = null;

	public ValidationException(String message, String detail) {
		super(message);
		if (detail != null) {
			errorDetail = detail;
		}
	}

	public ValidationException(String message, String detail, Object object) {
		this(message, detail);
		invalidObject = object;
	}

	public String getDetails() {
		return errorDetail;
	}
	
	public Object getObject() {
		return invalidObject;
	}
	
}
