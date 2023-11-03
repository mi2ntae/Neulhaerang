package com.finale.neulhaerang.global.exception.common;

public class InValidPageIndexException extends RuntimeException {
	public InValidPageIndexException(){
		super();
	}
	public InValidPageIndexException(String message){
		super(message);
	}
	public InValidPageIndexException(String message, Throwable th){
		super(message, th);
	}
}