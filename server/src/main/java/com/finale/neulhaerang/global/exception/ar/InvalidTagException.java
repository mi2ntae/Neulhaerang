package com.finale.neulhaerang.global.exception.ar;

public class InvalidTagException extends RuntimeException{
	public InvalidTagException(){
		super();
	}
	public InvalidTagException(String message){
		super(message);
	}
	public InvalidTagException(String message, Throwable th){
		super(message, th);
	}

}
