package com.finale.neulhaerang.domain.member.document;

import java.time.LocalDateTime;

import com.finale.neulhaerang.domain.routine.entity.StatType;

import lombok.Getter;

@Getter
public class StatRecord {
	private StatType statType;
	private LocalDateTime recordedDate;
	private int weight;
	private String reason;

	public void updateMongoTime() {
		this.recordedDate = this.recordedDate.plusHours(9);
	}
}
