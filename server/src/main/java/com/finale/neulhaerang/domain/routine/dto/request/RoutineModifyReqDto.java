package com.finale.neulhaerang.domain.routine.dto.request;

import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoutineModifyReqDto {
	@NotNull
	private Long routineId;
	@NotBlank
	private String content;
	@NotNull
	private List<Boolean> repeated;
	private boolean alarm;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
	private LocalTime alarmTime;

	@Builder
	private RoutineModifyReqDto(Long routineId, String content, List<Boolean> repeated, boolean alarm,
		LocalTime alarmTime) {
		this.routineId = routineId;
		this.content = content;
		this.repeated = repeated;
		this.alarm = alarm;
		this.alarmTime = alarmTime;
	}
}
