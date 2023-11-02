package com.finale.neulhaerang.domain.routine.dto.response;

import com.finale.neulhaerang.domain.routine.entity.Routine;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoutineResDto {
	private Long routineId;

	private String content;

	@Builder
	private RoutineResDto(Long routineId, String content) {
		this.routineId = routineId;
		this.content = content;
	}

	public static RoutineResDto from(Routine routine) {
		return RoutineResDto.builder()
			.routineId(routine.getId())
			.content(routine.getContent())
			.build();
	}
}
