package com.finale.neulhaerang.global.exception.todo;

public class InvalidTodoDateException extends RuntimeException {
	public InvalidTodoDateException(){
		super();
	}
	public InvalidTodoDateException(String message){
		super(message);
	}
	public InvalidTodoDateException(String message, Throwable th){
		super(message, th);
	}
}