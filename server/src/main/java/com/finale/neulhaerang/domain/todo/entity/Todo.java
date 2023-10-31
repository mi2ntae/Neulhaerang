package com.finale.neulhaerang.domain.todo.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "todo_id")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(nullable = false)
	private LocalDateTime todoDate;

	@Column(length = 100, nullable = false)
	private String content;

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean alarm;

	@ColumnDefault("false")
	@Column(name = "is_check", nullable = false)
	private boolean check;

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean status;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatType statType;

	public static Todo create(TodoCreateReqDto todoCreateReqDto, Member member) {
		return Todo.builder()
			.todoDate(todoCreateReqDto.getTodoDate())
			.content(todoCreateReqDto.getContent())
			.statType(todoCreateReqDto.getStatType())
			.member(member)
			.build();
	}
}