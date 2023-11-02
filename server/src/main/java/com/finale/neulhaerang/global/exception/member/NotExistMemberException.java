package com.finale.neulhaerang.global.exception.member;

public class NotExistMemberException extends RuntimeException {
	public NotExistMemberException(){
		super();
	}
	public NotExistMemberException(String message){
		super(message);
	}
	public NotExistMemberException(String message, Throwable th){
		super(message, th);
	}
}