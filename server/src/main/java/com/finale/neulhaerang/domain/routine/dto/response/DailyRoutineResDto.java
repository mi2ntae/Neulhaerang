package com.finale.neulhaerang.domain.routine.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailyRoutineResDto {
	private Long dailyRoutineId;
	private Long routineId;
	private String content;
	private boolean check;
	private LocalDate date;
}
