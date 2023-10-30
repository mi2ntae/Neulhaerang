package com.finale.neulhaerang.global.exception.member;

public class NonExistMemberException extends RuntimeException {
	public NonExistMemberException(){
		super();
	}
	public NonExistMemberException(String message){
		super(message);
	}
	public NonExistMemberException(String message, Throwable th){
		super(message, th);
	}
}