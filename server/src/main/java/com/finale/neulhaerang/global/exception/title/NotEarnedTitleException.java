package com.finale.neulhaerang.global.exception.title;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotEarnedTitleException extends RuntimeException {
	public NotEarnedTitleException() {
		super();
	}

	public NotEarnedTitleException(Member member, Long titleId) {
		super();
		log.error(
			member.getNickname() + "(member_id=" + member.getId()
				+ ")님이 title로 칭호를 변경하려 하셨는데 해당 id(id=" + titleId + ")를 가진 title은 아직 얻지 못했습니다.");
	}

	public NotEarnedTitleException(String message) {
		super(message);
	}

	public NotEarnedTitleException(String message, Throwable th) {
		super(message, th);
	}
}
