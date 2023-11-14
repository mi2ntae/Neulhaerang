package com.finale.neulhaerang.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.finale.neulhaerang.global.exception.ar.InvalidTagException;
import com.finale.neulhaerang.global.exception.common.AccessForbiddenException;
import com.finale.neulhaerang.global.exception.common.ExpiredAuthException;
import com.finale.neulhaerang.global.exception.common.InValidJwtTokenException;
import com.finale.neulhaerang.global.exception.common.InValidPageIndexException;
import com.finale.neulhaerang.global.exception.member.AlreadyExistTirednessException;
import com.finale.neulhaerang.global.exception.member.InvalidStatKindException;
import com.finale.neulhaerang.global.exception.member.NotExistCharacterInfoException;
import com.finale.neulhaerang.global.exception.member.NotExistDeviceException;
import com.finale.neulhaerang.global.exception.member.NotExistMemberException;
import com.finale.neulhaerang.global.exception.routine.AlreadyRemoveDailyRoutineException;
import com.finale.neulhaerang.global.exception.routine.AlreadyRemoveRoutineException;
import com.finale.neulhaerang.global.exception.routine.CanNotCreateDailyRoutineBeforeToday;
import com.finale.neulhaerang.global.exception.routine.CanNotRemoveBeforeTodayException;
import com.finale.neulhaerang.global.exception.routine.InvalidRepeatedDateException;
import com.finale.neulhaerang.global.exception.routine.NonRepeatedDateException;
import com.finale.neulhaerang.global.exception.routine.NotExistAlarmTimeException;
import com.finale.neulhaerang.global.exception.routine.NotExistDailyRoutineException;
import com.finale.neulhaerang.global.exception.routine.NotExistRelationWithRoutineException;
import com.finale.neulhaerang.global.exception.routine.NotExistRoutineException;
import com.finale.neulhaerang.global.exception.title.NotEarnedTitleException;
import com.finale.neulhaerang.global.exception.title.NotExistTitleException;
import com.finale.neulhaerang.global.exception.todo.InvalidTodoDateException;
import com.finale.neulhaerang.global.exception.todo.NotExistTodoException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CanNotCreateDailyRoutineBeforeToday.class)
	protected ResponseEntity<ErrorResponse> canNotCreateDailyRoutineBeforeToday() {
		log.error("Can not create daily routine before today.");
		ErrorResponse errorResponse = ErrorResponse.of(
			ErrorCode.CAN_NOT_CREATE_DAILY_ROUTINE_BEFORE_TODAY.getErrorCode(),
			ErrorCode.CAN_NOT_CREATE_DAILY_ROUTINE_BEFORE_TODAY.getMessage());
		return ResponseEntity.status(ErrorCode.CAN_NOT_CREATE_DAILY_ROUTINE_BEFORE_TODAY.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NonRepeatedDateException.class)
	protected ResponseEntity<ErrorResponse> nonRepeatedDateException() {
		log.error("Routine must have a repeat day.");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NON_REPEATED_DATE.getErrorCode(),
			ErrorCode.NON_REPEATED_DATE.getMessage());
		return ResponseEntity.status(ErrorCode.NON_REPEATED_DATE.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(AlreadyExistTirednessException.class)
	protected ResponseEntity<ErrorResponse> alreadyExistTirednessException() {
		log.error("Today's tiredness is already recorded");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.ALREADY_EXIST_TIREDNESS.getErrorCode(),
			ErrorCode.ALREADY_EXIST_TIREDNESS.getMessage());
		return ResponseEntity.status(ErrorCode.ALREADY_EXIST_TIREDNESS.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NotEarnedTitleException.class)
	protected ResponseEntity<ErrorResponse> notEarnedTitleException() {
		log.error("Login user cannot self tag");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_EARNED_TITLE.getErrorCode(),
			ErrorCode.NOT_EARNED_TITLE.getMessage());
		return ResponseEntity.status(ErrorCode.NOT_EARNED_TITLE.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(InvalidTagException.class)
	protected ResponseEntity<ErrorResponse> invalidTagException() {
		log.error("Login user cannot self tag");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_TAG.getErrorCode(),
			ErrorCode.INVALID_TAG.getMessage());
		return ResponseEntity.status(ErrorCode.INVALID_TAG.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NotExistTitleException.class)
	protected ResponseEntity<ErrorResponse> notExistTitleException() {
		log.error("Stat kind is not valid. Out of boundary");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_EXIST_TITLE.getErrorCode(),
			ErrorCode.NOT_EXIST_TITLE.getMessage());
		return ResponseEntity.status(ErrorCode.NOT_EXIST_TITLE.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(InValidPageIndexException.class)
	protected ResponseEntity<ErrorResponse> inValidPageIndexException() {
		log.error("page number is not valid");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_PAGE_INDEX.getErrorCode(),
			ErrorCode.INVALID_PAGE_INDEX.getMessage());
		return ResponseEntity.status(ErrorCode.INVALID_PAGE_INDEX.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(InvalidStatKindException.class)
	protected ResponseEntity<ErrorResponse> invalidStatKindException() {
		log.error("Stat kind is not valid. Out of boundary");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_STAT_KIND.getErrorCode(),
			ErrorCode.INVALID_STAT_KIND.getMessage());
		return ResponseEntity.status(ErrorCode.INVALID_STAT_KIND.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(CanNotRemoveBeforeTodayException.class)
	protected ResponseEntity<ErrorResponse> canNotRemoveBeforeTodayException() {
		log.error("Daily routine from before today cannot be deleted.");
		ErrorResponse errorResponse = ErrorResponse.of(
			ErrorCode.CAN_NOT_REMOVE_DAILY_ROUTINE_BEFORE_TODAY.getErrorCode(),
			ErrorCode.CAN_NOT_REMOVE_DAILY_ROUTINE_BEFORE_TODAY.getMessage());
		return ResponseEntity.status(ErrorCode.CAN_NOT_REMOVE_DAILY_ROUTINE_BEFORE_TODAY.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NotExistRelationWithRoutineException.class)
	protected ResponseEntity<ErrorResponse> notExistRelationWithRoutineException() {
		log.error("This daily routine is not related to that routine.");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_EXIST_RELATION_WITH_ROUTINE.getErrorCode(),
			ErrorCode.NOT_EXIST_RELATION_WITH_ROUTINE.getMessage());
		return ResponseEntity.status(ErrorCode.NOT_EXIST_RELATION_WITH_ROUTINE.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(AlreadyRemoveRoutineException.class)
	protected ResponseEntity<ErrorResponse> alreadyRemoveRoutineException() {
		log.error("The routine is already removed.");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.ALREADY_REMOVE_ROUTINE.getErrorCode(),
			ErrorCode.ALREADY_REMOVE_ROUTINE.getMessage());
		return ResponseEntity.status(ErrorCode.ALREADY_REMOVE_ROUTINE.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NotExistRoutineException.class)
	protected ResponseEntity<ErrorResponse> notExistRoutineException() {
		log.error("There is no routine with that id.");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_EXIST_ROUTINE.getErrorCode(),
			ErrorCode.NOT_EXIST_ROUTINE.getMessage());
		return ResponseEntity.status(ErrorCode.NOT_EXIST_ROUTINE.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(AlreadyRemoveDailyRoutineException.class)
	protected ResponseEntity<ErrorResponse> alreadyRemoveDailyRoutineException() {
		log.error("The daily routine is already removed.");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.ALREADY_REMOVE_DAILY_ROUTINE.getErrorCode(),
			ErrorCode.ALREADY_REMOVE_DAILY_ROUTINE.getMessage());
		return ResponseEntity.status(ErrorCode.ALREADY_REMOVE_DAILY_ROUTINE.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(NotExistDailyRoutineException.class)
	protected ResponseEntity<ErrorResponse> notExistDailyRoutineException() {
		log.error("There is no daily routine with that id.");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_EXIST_DAILY_ROUTINE.getErrorCode(),
			ErrorCode.NOT_EXIST_DAILY_ROUTINE.getMessage());
		return ResponseEntity.status(ErrorCode.NOT_EXIST_DAILY_ROUTINE.getHttpStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(InvalidRepeatedDateException.class)
	protected ResponseEntity<ErrorResponse> invalidRepeatedDateException() {
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

	@ExceptionHandler(InValidJwtTokenException.class)
	protected ResponseEntity<ErrorResponse> notValidJwtTokenException() {
		log.error("notvalid jwt");
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_TOKEN.getErrorCode(),
			ErrorCode.INVALID_TOKEN.getMessage());
		return ResponseEntity.status(ErrorCode.INVALID_TOKEN.getHttpStatus())
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