package com.finale.neulhaerang.domain.todo.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.todo.dto.request.TodoModifyReqDto;
import com.finale.neulhaerang.global.exception.todo.NotExistTodoException;
import com.finale.neulhaerang.global.util.BaseTest;
import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;
import com.finale.neulhaerang.domain.todo.dto.response.TodoListResDto;
import com.finale.neulhaerang.domain.todo.entity.Todo;
import com.finale.neulhaerang.domain.todo.repository.TodoRepository;
import com.finale.neulhaerang.global.exception.todo.InvalidTodoDateException;

class TodoServiceTest extends BaseTest {
	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private TodoService todoService;

	@Autowired
	private ModelMapper modelMapper;

	@Test
	@DisplayName("Todo 등록 테스트")
	public void When_CreateTodo_Expect_IsOk() {
		// given
		TodoCreateReqDto todoCreateReqDto = TodoCreateReqDto.builder()
			.todoDate(LocalDateTime.of(2023, 12, 1, 13, 30))
			.content("코딩테스트")
			.statType(StatType.생존력)
			.build();
		Todo todo = modelMapper.map(todoCreateReqDto, Todo.class);

		// when
		todoService.createTodo(todoCreateReqDto);

		// then
		List<Todo> todoList = todoRepository.findAll();
		assertThat(todoList).hasSize(1);
		assertThat(todoList.get(0)).usingRecursiveComparison()
			.ignoringFields("id", "member")
			.isEqualTo(todo);
	}

	@Test
	@DisplayName("Todo 등록 시 오늘보다 이전 날짜에 등록 요청이 온 경우 exception 발생 테스트")
	public void When_InsertWrongInput_Expect_BadRequest() {
		// given
		TodoCreateReqDto todoCreateReqDto = TodoCreateReqDto.builder()
			.todoDate(LocalDateTime.of(2023,10,1,13,30))
			.content("코딩테스트")
			.statType(StatType.생존력)
			.build();

		// when, then
		assertThatThrownBy(() -> todoService.createTodo(todoCreateReqDto))
			.isInstanceOf(InvalidTodoDateException.class);
	}

	@Test
	@DisplayName("해당 날짜에 Todo가 있을 경우 해당 날짜의 Todo만 리스트로 조회하는 테스트")
	public void When_FindTodo_Expect_TodoList() {
		// given
		Todo todo1 = this.createTodo("코딩테스트",StatType.생존력,LocalDateTime.of(2023,11,5,10,30));
		Todo todo2 = this.createTodo("찬구랑 점심먹기",StatType.인싸력,LocalDateTime.of(2023,11,1,12,30));
		Todo todo3 = this.createTodo("양치하기",StatType.갓생력,LocalDateTime.of(2023,11,1,13,30));
		Todo todo4 = this.createTodo("산책가기",StatType.튼튼력,LocalDateTime.of(2023,11,1,14,50));
		todoRepository.saveAll(List.of(todo1, todo2, todo3, todo4));

		LocalDate todoDate = LocalDate.of(2023,11,1);

		// when
		List<TodoListResDto> todoList = todoService.findTodoByDate(todoDate);

		// then
		assertThat(todoList).hasSize(3)
			.extracting("content","statType","alarm","check","alarmTime")
			.containsExactly(
				tuple("찬구랑 점심먹기",StatType.인싸력,false,false, LocalTime.of(12,30)),
				tuple("양치하기",StatType.갓생력,false,false,LocalTime.of(13,30)),
				tuple("산책가기",StatType.튼튼력,false,false,LocalTime.of(14,50))
			)
			.doesNotContain(
				tuple("코딩테스트",StatType.생존력,false,false,LocalTime.of(10,30))
			);
	}

	@Test
	@DisplayName("해당 날짜에 Todo가 없을 경우 null 반환 테스트")
	public void When_FindTodo_Expect_Null() {
		// given
		LocalDate todoDate = LocalDate.of(2023,12,1);

		// then
		List<TodoListResDto> todoList = todoService.findTodoByDate(todoDate);

		// when
		assertThat(todoList).isEmpty();
	}

	@Test
	@DisplayName("해당 Todo의 check 상태 변경 테스트")
	public void When_ModifyTodoCheck_Expect_isOk(){
		// given
		Todo todo = createTodo("아침 일찍 일어나기",StatType.갓생력,LocalDateTime.now());
		todoRepository.save(todo);

		// when
		todoService.modifyTodoCheckByTodoId(todo.getId());

		// then
		Todo checkTodo = todoRepository.findById(todo.getId()).get();
		assertThat(checkTodo.isCheck()).isEqualTo(true);
	}

	@Test
	@DisplayName("해당 Todo의 check 상태 변경시 존재하지 않거나 삭제 된 Todo일 경우 예외 발생 테스트")
	public void When_ModifyNotExistTodoCheck_Expect_NotExistTodoException(){
		// given
		Long todoId = 123L;
		Todo todo = Todo.builder()
			.member(member)
			.todoDate(LocalDateTime.now())
			.content("일찍 일어나기")
			.statType(StatType.갓생력)
			.status(true)
			.build();
		todoRepository.save(todo);

		// when, then
		assertThatThrownBy(() -> todoService.modifyTodoCheckByTodoId(todoId))
			.isInstanceOf(NotExistTodoException.class);
		assertThatThrownBy(() -> todoService.modifyTodoCheckByTodoId(todo.getId()))
			.isInstanceOf(NotExistTodoException.class);
	}

	@Test
	@DisplayName("오늘보다 이전 날짜의 Todo 체크시 예외 발생 테스트")
	public void When_ModifyTodoCheckBeforeToday_Expect_InvalidTodoDateException(){
		// given
		Todo todo = createTodo("아침 일찍 일어나기",StatType.갓생력,LocalDateTime.now().minusDays(1));
		todoRepository.save(todo);

		// when, then
		assertThatThrownBy(() -> todoService.modifyTodoCheckByTodoId(todo.getId()))
			.isInstanceOf(InvalidTodoDateException.class);
	}

	@Test
	@DisplayName("Todo id로 해당 Todo 삭제 테스트")
	public void When_RemoveTodo_Expect_isOk(){
		// given
		Todo todo = createTodo("알고리즘 풀기",StatType.창의력,LocalDateTime.now());
		todoRepository.save(todo);

		// when
		todoService.removeTodoByTodoId(todo.getId());

		// then
		Todo removeTodo = todoRepository.findById(todo.getId()).get();
		assertThat(removeTodo.isStatus()).isEqualTo(true);
	}

	@Test
	@DisplayName("Todo 삭제시 존재하지 않는 Todo일 경우 예외 발생 테스트")
	public void When_RemoveNotExistTodo_Expect_NotExistTodoException(){
		// given
		Long todoId = 123L;

		// when, then
		assertThatThrownBy(() -> todoService.removeTodoByTodoId(todoId))
			.isInstanceOf(NotExistTodoException.class);
	}

	@Test
	@DisplayName("오늘보다 이전 날짜의 Todo 삭제시 예외 발생 테스트")
	public void When_RemoveTodoBeforeToday_Expect_InvalidTodoDateException(){
		// given
		Todo todo = createTodo("알고리즘 풀기",StatType.창의력,LocalDateTime.now().minusDays(1));
		todoRepository.save(todo);

		// when, then
		assertThatThrownBy(() -> todoService.removeTodoByTodoId(todo.getId()))
			.isInstanceOf(InvalidTodoDateException.class);
	}

	@Test
	@DisplayName("Todo id로 해당 Todo 수정 테스트")
	public void When_ModifyTodo_Expect_isOk(){
		// given
		LocalDateTime localDateTime = LocalDateTime.now();
		Todo todo = createTodo("헬스가기",StatType.튼튼력,localDateTime);
		todoRepository.save(todo);
		TodoModifyReqDto todoModifyReqDto = createTodoModifyReqDto(
			"산책하기", StatType.튼튼력, LocalDateTime.now().plusDays(1), true
		);

		// when
		todoService.modifyTodoByTodoId(todo.getId(), todoModifyReqDto);

		// then
		Todo modifyTodo = todoRepository.findById(todo.getId()).get();
		assertThat(modifyTodo)
			.extracting("content","alarm","statType","todoDate")
			.contains("산책하기",true,StatType.튼튼력, localDateTime.plusDays(1))
		;
	}

	private Todo createTodo(String content, StatType statType, LocalDateTime todoDate){
		return Todo.builder()
			.member(member)
			.todoDate(todoDate)
			.content(content)
			.statType(statType)
			.build();
	}

	private TodoModifyReqDto createTodoModifyReqDto(String content, StatType statType, LocalDateTime todoDate, Boolean alarm){
		return TodoModifyReqDto.builder()
			.alarm(alarm)
			.content(content)
			.todoDate(todoDate)
			.statType(statType)
			.build();
	}
}