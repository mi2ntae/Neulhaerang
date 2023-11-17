package com.finale.neulhaerang.global.event;

import com.finale.neulhaerang.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class WeatherEvent {
	private Member member;

	public WeatherEvent(Member member) {
		this.member = member;
	}
}
