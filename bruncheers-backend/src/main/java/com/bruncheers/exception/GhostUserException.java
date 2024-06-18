package com.bruncheers.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GhostUserException extends RuntimeException{
	 public GhostUserException(String message) {
	        super(message);
	    }
}
