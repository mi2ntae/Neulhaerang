package com.finale.neulhaerang.domain.todo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.todo.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	Optional<Todo> findTodoByIdAndStatusIsFalse(Long todoId);

	List<Todo> findTodosByMemberAndStatusIsFalseAndTodoDateIsBetween(Member member, LocalDateTime startDate,
		LocalDateTime endDate);

	List<Todo> findTodosByMemberAndStatusIsFalseAndCheckIsTrueAndTodoDateIsBetween(Member member,
		LocalDateTime startDate, LocalDateTime endDate);

	List<Todo> findTodosByStatusIsFalseAndAlarmIsTrueAndTodoDateIsBetween(LocalDateTime startDateTime,
		LocalDateTime endDateTime);
}
