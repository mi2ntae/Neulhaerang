package com.finale.neulhaerang.domain.todo.dto.response;

import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.todo.entity.Todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoListResDto {
	private String content;
	private boolean alarm;
	private boolean check;
	private StatType statType;
	private String alarmTime;

	public static TodoListResDto of(Todo todo, String alarmTime){
		return TodoListResDto.builder()
			.content(todo.getContent())
			.alarm(todo.isAlarm())
			.check(todo.isCheck())
			.statType(todo.getStatType())
			.alarmTime(alarmTime)
			.build();
	}
}
