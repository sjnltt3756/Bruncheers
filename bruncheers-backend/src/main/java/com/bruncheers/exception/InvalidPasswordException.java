package com.bruncheers.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidPasswordException extends Exception{
	
	 public InvalidPasswordException(String message) {
	        super(message);
	    }
}
