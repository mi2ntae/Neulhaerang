package com.finale.neulhaerang.global.exception.member;

public class NonExistDeviceException extends RuntimeException {
	public NonExistDeviceException(){
		super();
	}
	public NonExistDeviceException(String message){
		super(message);
	}
	public NonExistDeviceException(String message, Throwable th){
		super(message, th);
	}
}