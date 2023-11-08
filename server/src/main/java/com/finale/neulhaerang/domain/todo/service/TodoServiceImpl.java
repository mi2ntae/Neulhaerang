package com.finale.neulhaerang.domain.todo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.routine.repository.DailyRoutineRepository;
import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;
import com.finale.neulhaerang.domain.todo.dto.request.TodoModifyReqDto;
import com.finale.neulhaerang.domain.todo.dto.response.CheckRatioListResDto;
import com.finale.neulhaerang.domain.todo.dto.response.TodoListResDto;
import com.finale.neulhaerang.domain.todo.entity.Todo;
import com.finale.neulhaerang.domain.todo.repository.TodoRepository;
import com.finale.neulhaerang.global.exception.todo.InvalidTodoDateException;
import com.finale.neulhaerang.global.exception.todo.NotExistTodoException;
import com.finale.neulhaerang.global.handler.AuthenticationHandler;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
	private final TodoRepository todoRepository;
	private final AuthenticationHandler authenticationHandler;
	private final MemberRepository memberRepository;
	private final DailyRoutineRepository dailyRoutineRepository;

	@Override
	public void createTodo(TodoCreateReqDto todoCreateReqDto) {
		if (todoCreateReqDto.getTodoDate().isBefore(LocalDateTime.now())) {
			throw new InvalidTodoDateException();
		}
		Member member = memberRepository.getReferenceById(authenticationHandler.getLoginMemberId());
		todoRepository.save(Todo.create(todoCreateReqDto, member));
	}

	@Override
	public List<TodoListResDto> findTodoByDate(LocalDate todoDate) {
		Member member = memberRepository.getReferenceById(authenticationHandler.getLoginMemberId());

		LocalDateTime startDate = todoDate.atStartOfDay();
		LocalDateTime endDate = todoDate.atTime(LocalTime.MAX);

		List<Todo> todoList = todoRepository.findTodosByMemberAndStatusIsFalseAndTodoDateIsBetween(member, startDate,
			endDate);

		return todoList.stream()
			.map(TodoListResDto::from)
			.collect(Collectors.toList());
	}

	@Override
	public void modifyTodoCheckByTodoId(Long todoId) {
		Todo todo = todoRepository.findTodoByIdAndStatusIsFalse(todoId)
			.orElseThrow(NotExistTodoException::new);
		if (todo.getTodoDate().toLocalDate().isBefore(LocalDate.now())) {
			throw new InvalidTodoDateException();
		}
		todo.updateCheck();
	}

	@Override
	public void removeTodoByTodoId(Long todoId) {
		Todo todo = todoRepository.findTodoByIdAndStatusIsFalse(todoId)
			.orElseThrow(NotExistTodoException::new);
		if (todo.getTodoDate().toLocalDate().isBefore(LocalDate.now())) {
			throw new InvalidTodoDateException();
		}
		todo.updateStatus();
	}

	@Override
	public void modifyTodoByTodoId(Long todoId, TodoModifyReqDto todoModifyReqDto) {
		Todo todo = todoRepository.findTodoByIdAndStatusIsFalse(todoId)
			.orElseThrow(NotExistTodoException::new);
		if (
			todo.getTodoDate().toLocalDate().isBefore(LocalDate.now()) || todoModifyReqDto.getTodoDate()
				.toLocalDate()
				.isBefore(LocalDate.now())) {
			throw new InvalidTodoDateException();
		}
		todo.updateTodo(todoModifyReqDto);
	}

	@Override
	public List<CheckRatioListResDto> findCheckRatioByMonth(YearMonth yearMonth) {
		Member member = memberRepository.getReferenceById(authenticationHandler.getLoginMemberId());

		LocalDate start = yearMonth.atDay(1);
		LocalDate end = yearMonth.atEndOfMonth();
		if (YearMonth.from(LocalDate.now()).equals(yearMonth)) {
			end = LocalDate.now();
		}

		List<CheckRatioListResDto> checkRatioList = new ArrayList<>();
		for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
			int totalTodo = todoRepository.findTodosByMemberAndStatusIsFalseAndTodoDateIsBetween(member,
				date.atStartOfDay(), date.atTime(LocalTime.MAX)).size();
			int totalRoutine = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutine_MemberAndStatusIsFalse(
				date, member).size();
			int checkTodo = todoRepository.findTodosByMemberAndStatusIsFalseAndCheckIsTrueAndTodoDateIsBetween(member,
				date.atStartOfDay(), date.atTime(LocalTime.MAX)).size();
			int checkRoutine = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutine_MemberAndStatusIsFalseAndCheckIsTrue(
				date, member).size();

			double ratio = (double)(checkTodo + checkRoutine) / (totalTodo + totalRoutine);
			if (Double.isNaN(ratio)) {
				ratio = 0;
			}
			checkRatioList.add(CheckRatioListResDto.of(date, ratio));
		}

		return checkRatioList;
	}
}
