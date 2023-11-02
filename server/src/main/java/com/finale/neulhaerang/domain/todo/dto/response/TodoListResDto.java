package com.finale.neulhaerang.domain.todo.dto.response;

import java.time.LocalTime;

import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.todo.entity.Todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TodoListResDto {
	private String content;
	private boolean alarm;
	private boolean check;
	private StatType statType;
	private LocalTime alarmTime;

	public static TodoListResDto from(Todo todo){
		return TodoListResDto.builder()
			.content(todo.getContent())
			.alarm(todo.isAlarm())
			.check(todo.isCheck())
			.statType(todo.getStatType())
			.alarmTime(todo.getTodoDate().toLocalTime())
			.build();
	}
}
