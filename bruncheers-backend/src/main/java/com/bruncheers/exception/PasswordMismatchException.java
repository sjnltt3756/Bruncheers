package com.bruncheers.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordMismatchException extends RuntimeException {
	private Object data;
	public PasswordMismatchException(String msg) {
		super(msg);
	}
	
	
	
}
