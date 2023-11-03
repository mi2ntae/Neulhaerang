package com.finale.neulhaerang.domain.member.dto.response;

import java.time.LocalDate;

import com.finale.neulhaerang.domain.member.document.StatRecord;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StatRecordListResDto {
	private String reason;
	private LocalDate recordedDate;
	private int weight;

	public static StatRecordListResDto from(StatRecord statRecord) {
		return StatRecordListResDto.builder()
			.reason(statRecord.getReason())
			.recordedDate(statRecord.getRecordedDate().toLocalDate())
			.weight(statRecord.getWeight()).build();
	}
}