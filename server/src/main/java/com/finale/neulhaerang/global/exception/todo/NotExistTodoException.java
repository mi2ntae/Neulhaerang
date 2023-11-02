package com.finale.neulhaerang.global.exception.todo;

public class NotExistTodoException extends RuntimeException {
	public NotExistTodoException(){
		super();
	}
	public NotExistTodoException(String message){
		super(message);
	}
	public NotExistTodoException(String message, Throwable th){
		super(message, th);
	}
}