package com.finale.neulhaerang.domain.member.dto.request;

import java.time.LocalDateTime;

import com.finale.neulhaerang.domain.routine.entity.StatType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatRecordReqDto {
	private String reason;
	private LocalDateTime recordedDate;
	private StatType statType;
	private int weight;
}