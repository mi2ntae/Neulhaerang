package com.finale.neulhaerang.domain.routine.dto.response;

import java.time.LocalTime;
import java.util.List;

import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.entity.StatType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineResDto {
	private Long routineId;
	private String content;
	private StatType statType;
	private boolean alarm;
	private LocalTime alarmTime;
	private List<Boolean> repeated;

	public static RoutineResDto of(Routine routine, List<Boolean> repeated) {
		return RoutineResDto.builder()
			.routineId(routine.getId())
			.content(routine.getContent())
			.alarm(routine.isAlarm())
			.alarmTime(routine.getAlarmTime())
			.statType(routine.getStatType())
			.repeated(repeated)
			.build();
	}
}
