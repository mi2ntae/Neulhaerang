package com.finale.neulhaerang.global.exception.todo;

public class AlreadyRemoveTodoException extends RuntimeException {
	public AlreadyRemoveTodoException(){
		super();
	}
	public AlreadyRemoveTodoException(String message){
		super(message);
	}
	public AlreadyRemoveTodoException(String message, Throwable th){
		super(message, th);
	}
}