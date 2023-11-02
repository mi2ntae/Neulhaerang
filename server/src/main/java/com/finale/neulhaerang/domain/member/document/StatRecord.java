package com.finale.neulhaerang.domain.member.document;

import java.time.LocalDateTime;

import com.finale.neulhaerang.domain.routine.entity.StatType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatRecord {
	private StatType statType;
	private LocalDateTime recordedDate;
	private int weight;
	private String reason;

	public static StatRecord of(StatType statType, LocalDateTime recordedDate, int weight, String reason) {
		return StatRecord.builder()
			.statType(statType)
			.recordedDate(recordedDate)
			.weight(weight)
			.reason(reason).build();
	}

	public void updateMongoTime() {
		this.recordedDate = this.recordedDate.plusHours(9);
	}
}
