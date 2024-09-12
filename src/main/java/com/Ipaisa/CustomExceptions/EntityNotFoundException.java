package com.Ipaisa.CustomExceptions;

public class EntityNotFoundException extends RuntimeException{
	public EntityNotFoundException(String message) {
        super(message);
    }

}
