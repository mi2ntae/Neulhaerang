package com.finale.neulhaerang.global.exception.member;

public class NonExistCharacterInfoException extends RuntimeException{
	public NonExistCharacterInfoException(){
		super();
	}
	public NonExistCharacterInfoException(String message){
		super(message);
	}
	public NonExistCharacterInfoException(String message, Throwable th){
		super(message, th);
	}

}
