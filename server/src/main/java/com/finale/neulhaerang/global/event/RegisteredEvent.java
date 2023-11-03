package com.finale.neulhaerang.global.event;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class RegisteredEvent {
	private Member member;

	public RegisteredEvent(Member member) {
		this.member = member;
	}
}
