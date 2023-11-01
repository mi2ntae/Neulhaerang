package com.finale.neulhaerang.global.exception.member;

public class NotExistDeviceException extends RuntimeException {
	public NotExistDeviceException(){
		super();
	}
	public NotExistDeviceException(String message){
		super(message);
	}
	public NotExistDeviceException(String message, Throwable th){
		super(message, th);
	}
}