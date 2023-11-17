package com.finale.neulhaerang.global.event;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class RepelIndolenceMonsterEvent {
	private Member member;

	public RepelIndolenceMonsterEvent(Member member) {
		this.member = member;
	}
}
