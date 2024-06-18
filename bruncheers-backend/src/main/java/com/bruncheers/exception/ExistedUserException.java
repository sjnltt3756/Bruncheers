package com.bruncheers.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExistedUserException extends RuntimeException{
	private Object data;
	public ExistedUserException(String msg) {
		super(msg);
	}
	
	
	
}
