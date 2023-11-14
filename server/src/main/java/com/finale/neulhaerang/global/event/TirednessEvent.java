package com.finale.neulhaerang.global.event;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class TirednessEvent {
	private Member member;

	public TirednessEvent(Member member) {
		this.member = member;
	}
}
