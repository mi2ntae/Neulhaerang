package com.finale.neulhaerang.global.exception.common;

public class ExpiredAuthException extends RuntimeException {
	public ExpiredAuthException(){
		super();
	}
	public ExpiredAuthException(String message){
		super(message);
	}
	public ExpiredAuthException(String message, Throwable th){
		super(message, th);
	}
}