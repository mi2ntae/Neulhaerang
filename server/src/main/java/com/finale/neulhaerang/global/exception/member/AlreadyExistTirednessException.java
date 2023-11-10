package com.finale.neulhaerang.global.exception.member;

public class AlreadyExistTirednessException extends RuntimeException{
	public AlreadyExistTirednessException(){
		super();
	}
	public AlreadyExistTirednessException(String message){
		super(message);
	}
	public AlreadyExistTirednessException(String message, Throwable th){
		super(message, th);
	}

}
