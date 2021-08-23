package com.kinsight.exception;

public abstract class AbstractBaseException extends RuntimeException {

	private static final long serialVersionUID = 8342235231880246631L;

	protected BaseResponseCode responseCode;
	protected Object[] args;
	protected String arg;

	public AbstractBaseException() {
	}

	public AbstractBaseException(BaseResponseCode responseCode) {
		this.responseCode = responseCode;
	}

	public BaseResponseCode getResponseCode() {
		return responseCode;
	}

	public Object[] getArgs() {
		return args;
	}
	
	public String getArg() {
		return arg;
	}

}