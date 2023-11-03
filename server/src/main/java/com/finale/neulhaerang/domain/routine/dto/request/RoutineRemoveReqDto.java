package com.finale.neulhaerang.domain.routine.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoutineRemoveReqDto {
	@NotNull
	private Long dailyRoutineId;
	@NotNull
	private Long routineId;
	private boolean never;

	@Builder
	private RoutineRemoveReqDto(Long dailyRoutineId, Long routineId, boolean never) {
		this.dailyRoutineId = dailyRoutineId;
		this.routineId = routineId;
		this.never = never;
	}
}
