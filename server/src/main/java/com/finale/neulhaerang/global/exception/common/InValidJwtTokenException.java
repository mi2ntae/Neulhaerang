package com.finale.neulhaerang.global.exception.common;

public class InValidJwtTokenException extends RuntimeException {
	public InValidJwtTokenException(){
		super();
	}
	public InValidJwtTokenException(String message){
		super(message);
	}
	public InValidJwtTokenException(String message, Throwable th){
		super(message, th);
	}
}