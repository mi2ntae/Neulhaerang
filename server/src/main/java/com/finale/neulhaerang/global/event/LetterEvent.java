package com.finale.neulhaerang.global.event;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class LetterEvent {
	private Member member;

	public LetterEvent(Member member) {
		this.member = member;
	}
}
