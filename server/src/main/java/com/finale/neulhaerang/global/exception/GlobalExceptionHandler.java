package com.finale.neulhaerang.global.exception;

import java.net.BindException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.finale.neulhaerang.global.exception.common.AccessForbiddenException;
import com.finale.neulhaerang.global.exception.common.ExpiredAuthException;
import com.finale.neulhaerang.global.exception.common.NonValidJwtTokenException;
import com.finale.neulhaerang.global.exception.member.NonExistCharacterInfoException;
import com.finale.neulhaerang.global.exception.member.NonExistDeviceException;
import com.finale.neulhaerang.global.exception.member.NonExistMemberException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AccessForbiddenException.class)
	protected ResponseEntity<ErrorResponse> accessForbiddenException() {
		log.error("authentication fail request member is not login member");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.ACCESS_FORBIDDEN.getErrorCode(), ErrorCode.ACCESS_FORBIDDEN.getMessage());
		return ResponseEntity.status(ErrorCode.ACCESS_FORBIDDEN.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(ExpiredAuthException.class)
	protected ResponseEntity<ErrorResponse> expiredAuthException() {
		log.error("refresh token expired login again");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.EXPIRED_AUTH.getErrorCode(), ErrorCode.EXPIRED_AUTH.getMessage());
		return ResponseEntity.status(ErrorCode.EXPIRED_AUTH.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NonExistMemberException.class)
	protected ResponseEntity<ErrorResponse> nonExistMemberException() {
		log.error("member not exist");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NON_EXIST_MEMBER.getErrorCode(), ErrorCode.NON_EXIST_MEMBER.getMessage());
		return ResponseEntity.status(ErrorCode.NON_EXIST_MEMBER.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NonExistDeviceException.class)
	protected ResponseEntity<ErrorResponse> nonExistDeviceException() {
		log.error("login device is not valid");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NON_EXIST_DEVICE.getErrorCode(), ErrorCode.NON_EXIST_DEVICE.getMessage());
		return ResponseEntity.status(ErrorCode.NON_EXIST_DEVICE.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NonExistCharacterInfoException.class)
	protected ResponseEntity<ErrorResponse> nonExistCharacterInfoException() {
		log.error("character info not found");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NON_EXIST_CHARACTERINFO.getErrorCode(), ErrorCode.NON_EXIST_CHARACTERINFO.getMessage());
		return ResponseEntity.status(ErrorCode.NON_EXIST_CHARACTERINFO.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NonValidJwtTokenException.class)
	protected ResponseEntity<ErrorResponse> nonValidJwtTokenException() {
		log.error("nonvalid jwt");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NON_VALID_TOKEN.getErrorCode(), ErrorCode.NON_VALID_TOKEN.getMessage());
		return ResponseEntity.status(ErrorCode.NON_VALID_TOKEN.getHttpStatus())
			.body(errorResponse);
	}


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