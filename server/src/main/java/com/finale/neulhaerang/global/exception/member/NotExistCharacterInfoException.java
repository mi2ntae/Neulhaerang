package com.finale.neulhaerang.global.exception.member;

public class NotExistCharacterInfoException extends RuntimeException{
	public NotExistCharacterInfoException(){
		super();
	}
	public NotExistCharacterInfoException(String message){
		super(message);
	}
	public NotExistCharacterInfoException(String message, Throwable th){
		super(message, th);
	}

}
