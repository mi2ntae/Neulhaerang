package com.finale.neulhaerang.global.exception.common;

public class AccessForbiddenException extends RuntimeException {
	public AccessForbiddenException(){
		super();
	}
	public AccessForbiddenException(String message){
		super(message);
	}
	public AccessForbiddenException(String message, Throwable th){
		super(message, th);
	}
}