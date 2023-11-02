package com.finale.neulhaerang.global.exception.common;

public class NotValidJwtTokenException extends RuntimeException {
	public NotValidJwtTokenException(){
		super();
	}
	public NotValidJwtTokenException(String message){
		super(message);
	}
	public NotValidJwtTokenException(String message, Throwable th){
		super(message, th);
	}
}