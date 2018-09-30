package com.ntrungnghia.exception;

public class PiAppBaseException extends Exception {

	private static final long serialVersionUID = -2267519281929803740L;

	private String msg = "";
	
	public PiAppBaseException(String message) {
		this.msg = message;
	}

	@Override
	public String getMessage() {
		return this.msg;
	}
}
