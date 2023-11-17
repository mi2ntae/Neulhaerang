package com.finale.neulhaerang.domain.routine.dto.request;

import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finale.neulhaerang.domain.routine.entity.StatType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoutineCreateReqDto {
	@NotBlank
	private String content;
	@NotNull
	private List<Boolean> repeated;
	@NotNull
	private boolean alarm;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
	private LocalTime alarmTime;
	@NotNull
	private StatType statType;

	@Builder
	private RoutineCreateReqDto(String content, List<Boolean> repeated, boolean alarm, LocalTime alarmTime,
		StatType statType) {
		this.content = content;
		this.repeated = repeated;
		this.alarm = alarm;
		this.alarmTime = alarmTime;
		this.statType = statType;
	}
}
