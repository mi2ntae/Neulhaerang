package com.finale.neulhaerang.global.exception.common;

public class RedisConnectionException extends RuntimeException {
	public RedisConnectionException(){
		super();
	}
	public RedisConnectionException(String message){
		super(message);
	}
	public RedisConnectionException(String message, Throwable th){
		super(message, th);
	}
}