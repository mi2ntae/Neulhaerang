package com.finale.neulhaerang.global.event;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class StatEvent {
	private Member member;

	public StatEvent(Member member) {
		this.member = member;
	}
}
