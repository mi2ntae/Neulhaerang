package com.finale.neulhaerang.domain.routine.dto.request;

import javax.validation.constraints.NotNull;

public class RoutineRemoveReqDto {
	@NotNull
	private Long dailyRoutineId;
	@NotNull
	private Long routineId;
	private boolean never;
}
