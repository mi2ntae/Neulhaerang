package com.finale.neulhaerang.global.event;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class TagOtherMemberEvent {
	private Member member;

	public TagOtherMemberEvent(Member member) {
		this.member = member;
	}
}
