package com.finale.neulhaerang.global.exception.member;

public class InvalidStatKindException extends RuntimeException{
	public InvalidStatKindException(){
		super();
	}
	public InvalidStatKindException(String message){
		super(message);
	}
	public InvalidStatKindException(String message, Throwable th){
		super(message, th);
	}

}
