package com.finale.neulhaerang.domain.routine.dto.response;

import java.time.LocalTime;
import java.util.List;

import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;
import com.finale.neulhaerang.domain.routine.entity.StatType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyRoutineResDto {
	private Long dailyRoutineId;
	private Long routineId;
	private String content;
	private boolean check;
	private boolean alarm;
	private LocalTime alarmTime;
	private StatType statType;
	private List<Boolean> repeated;

	public static DailyRoutineResDto of(DailyRoutine dailyRoutine, List<Boolean> repeated) {
		return DailyRoutineResDto.builder()
			.dailyRoutineId(dailyRoutine.getId())
			.routineId(dailyRoutine.getRoutine().getId())
			.content(dailyRoutine.getRoutine().getContent())
			.check(dailyRoutine.isCheck())
			.alarm(dailyRoutine.getRoutine().isAlarm())
			.alarmTime(dailyRoutine.getRoutine().getAlarmTime())
			.statType(dailyRoutine.getRoutine().getStatType())
			.repeated(repeated)
			.build();
	}
}
