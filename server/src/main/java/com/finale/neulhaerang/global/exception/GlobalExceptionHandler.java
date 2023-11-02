package com.finale.neulhaerang.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.finale.neulhaerang.global.exception.common.AccessForbiddenException;
import com.finale.neulhaerang.global.exception.common.ExpiredAuthException;
import com.finale.neulhaerang.global.exception.common.InvalidRepeatedDateException;
import com.finale.neulhaerang.global.exception.common.NotValidJwtTokenException;
import com.finale.neulhaerang.global.exception.common.NotExistAlarmTimeException;
import com.finale.neulhaerang.global.exception.member.NotExistCharacterInfoException;
import com.finale.neulhaerang.global.exception.member.NotExistDeviceException;
import com.finale.neulhaerang.global.exception.member.NotExistMemberException;
import com.finale.neulhaerang.global.exception.todo.AlreadyRemoveTodoException;
import com.finale.neulhaerang.global.exception.todo.InvalidTodoDateException;
import com.finale.neulhaerang.global.exception.todo.NotExistTodoException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(InvalidRepeatedDateException.class)
	protected ResponseEntity<ErrorResponse> InvalidRepeatedDateException() {
		log.error("The repeat date information is incorrect. for all days of the week.");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_REPEATED_DATE.getErrorCode(),
			ErrorCode.INVALID_REPEATED_DATE.getMessage());
		return ResponseEntity.status(ErrorCode.INVALID_REPEATED_DATE.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(InvalidTodoDateException.class)
	protected ResponseEntity<ErrorResponse> invalidTodoDateException() {
		log.error("create todo is fail. todo date is before today");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_TODO_DATE.getErrorCode(),
			ErrorCode.INVALID_TODO_DATE.getMessage());
		return ResponseEntity.status(ErrorCode.INVALID_TODO_DATE.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NotExistTodoException.class)
	protected ResponseEntity<ErrorResponse> notExistTodoException() {
		log.error("todo is not exist");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_EXIST_TODO.getErrorCode(),
			ErrorCode.NOT_EXIST_TODO.getMessage());
		return ResponseEntity.status(ErrorCode.NOT_EXIST_TODO.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(AlreadyRemoveTodoException.class)
	protected ResponseEntity<ErrorResponse> alreadyRemoveTodoException() {
		log.error("todo is not exist");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.ALREADY_REMOVE_TODO.getErrorCode(),
			ErrorCode.ALREADY_REMOVE_TODO.getMessage());
		return ResponseEntity.status(ErrorCode.ALREADY_REMOVE_TODO.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(AccessForbiddenException.class)
	protected ResponseEntity<ErrorResponse> accessForbiddenException() {
		log.error("authentication fail request member is not login member");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.ACCESS_FORBIDDEN.getErrorCode(),
			ErrorCode.ACCESS_FORBIDDEN.getMessage());
		return ResponseEntity.status(ErrorCode.ACCESS_FORBIDDEN.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NotExistAlarmTimeException.class)
	protected ResponseEntity<ErrorResponse> notExistAlarmTime() {
		log.error("not exist alarm time if get an alarm");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_EXIST_ALARM_TIME.getErrorCode(),
			ErrorCode.NOT_EXIST_ALARM_TIME.getMessage());
		return ResponseEntity.status(ErrorCode.NOT_EXIST_ALARM_TIME.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(ExpiredAuthException.class)
	protected ResponseEntity<ErrorResponse> expiredAuthException() {
		log.error("refresh token expired login again");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.EXPIRED_AUTH.getErrorCode(),
			ErrorCode.EXPIRED_AUTH.getMessage());
		return ResponseEntity.status(ErrorCode.EXPIRED_AUTH.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NotExistMemberException.class)
	protected ResponseEntity<ErrorResponse> notExistMemberException() {
		log.error("member not exist");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_EXIST_MEMBER.getErrorCode(),
			ErrorCode.NOT_EXIST_MEMBER.getMessage());
		return ResponseEntity.status(ErrorCode.NOT_EXIST_MEMBER.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NotExistDeviceException.class)
	protected ResponseEntity<ErrorResponse> notExistDeviceException() {
		log.error("login device is not valid");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_EXIST_DEVICE.getErrorCode(),
			ErrorCode.NOT_EXIST_DEVICE.getMessage());
		return ResponseEntity.status(ErrorCode.NOT_EXIST_DEVICE.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NotExistCharacterInfoException.class)
	protected ResponseEntity<ErrorResponse> notExistCharacterInfoException() {
		log.error("character info not found");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_EXIST_CHARACTERINFO.getErrorCode(),
			ErrorCode.NOT_EXIST_CHARACTERINFO.getMessage());
		return ResponseEntity.status(ErrorCode.NOT_EXIST_CHARACTERINFO.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NotValidJwtTokenException.class)
	protected ResponseEntity<ErrorResponse> notValidJwtTokenException() {
		log.error("notvalid jwt");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_VALID_TOKEN.getErrorCode(),
			ErrorCode.NOT_VALID_TOKEN.getMessage());
		return ResponseEntity.status(ErrorCode.NOT_VALID_TOKEN.getHttpStatus())
			.body(errorResponse);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
		log.error("handleBindException", e);
		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException e) {
		log.error("handleMethodArgumentTypeMismatchException", e);
		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(errorResponse);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException e) {
		log.error("handleHttpRequestMethodNotSupportedException", e);
		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED.toString(), e.getMessage());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handlerException(Exception e) {
		log.error("Exception", e);
		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
}