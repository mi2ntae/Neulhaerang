package com.finale.neulhaerang.domain.routine.dto.response;

import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DailyRoutineResDto {
	private Long dailyRoutineId;

	private Long routineId;
	private String content;
	private boolean check;

	@Builder
	private DailyRoutineResDto(Long dailyRoutineId, Long routineId, String content, boolean check) {
		this.dailyRoutineId = dailyRoutineId;
		this.routineId = routineId;
		this.content = content;
		this.check = check;
	}

	public static DailyRoutineResDto from(DailyRoutine dailyRoutine) {
		return DailyRoutineResDto.builder()
			.dailyRoutineId(dailyRoutine.getId())
			.routineId(dailyRoutine.getRoutine().getId())
			.content(dailyRoutine.getRoutine().getContent())
			.check(dailyRoutine.isCheck())
			.build();
	}
}
