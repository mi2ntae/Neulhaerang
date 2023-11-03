package com.finale.neulhaerang.global.exception.title;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotExistTitleException extends RuntimeException {
	public NotExistTitleException() {
		super();
	}

	public NotExistTitleException(Member member, Long titleId) {
		super();
		log.error(
			member.getNickname() + "(member_id=" + member.getId()
				+ ")님이 title을 얻으려고 하셨는데 해당 id(id=" + titleId + ")를 가진 title은 존재하지 않습니다.");
	}

	public NotExistTitleException(String message) {
		super(message);
	}

	public NotExistTitleException(String message, Throwable th) {
		super(message, th);
	}
}