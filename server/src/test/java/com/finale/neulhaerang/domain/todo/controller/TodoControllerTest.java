package com.finale.neulhaerang.domain.todo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.todo.dto.request.TodoModifyReqDto;
import com.finale.neulhaerang.domain.todo.entity.Todo;
import com.finale.neulhaerang.domain.todo.repository.TodoRepository;
import com.finale.neulhaerang.global.util.BaseTest;
import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;

@AutoConfigureMockMvc
@WithMockUser(password = "1")
class TodoControllerTest extends BaseTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TodoRepository todoRepository;

	@Test
	@DisplayName("Todo 등록 테스트")
	public void When_CreateTodo_Expect_IsOk() throws Exception {
		// given
		TodoCreateReqDto todoCreateReqDto = TodoCreateReqDto.builder()
			.todoDate(LocalDateTime.now().plusDays(1))
			.content("코딩테스트")
			.statType(StatType.생존력)
			.build();

		// when, then
		mockMvc.perform(post("/todo/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todoCreateReqDto))
			)
			.andDo(print())
			.andExpect(status().isCreated())
		;
	}

	@Test
	@DisplayName("Todo 등록 시 날짜에 빈 값이 들어올 경우 에러 발생 테스트")
	public void When_InsertEmptyDate_Expect_BadRequest() throws Exception {
		// given
		TodoCreateReqDto todoCreateReqDto = TodoCreateReqDto.builder()
			.content("코딩테스트")
			.statType(StatType.생존력)
			.build();

		// when, then
		mockMvc.perform(post("/todo/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todoCreateReqDto))
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
		;
	}

	@Test
	@DisplayName("Todo 등록 시 스탯타입에 빈 값이 들어올 경우 에러 발생 테스트")
	public void When_InsertEmptyStatType_Expect_BadRequest() throws Exception {
		// given
		TodoCreateReqDto todoCreateReqDto = TodoCreateReqDto.builder()
			.todoDate(LocalDateTime.of(2023,11,1,13,30))
			.content("코딩테스트")
			.build();

		// when, then
		mockMvc.perform(post("/todo/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todoCreateReqDto))
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
		;
	}

	@Test
	@DisplayName("Todo 등록 시 내용에 빈 값이 들어올 경우 에러 발생 테스트")
	public void When_InsertEmptyContent_Expect_BadRequest() throws Exception {
		// given
		TodoCreateReqDto todoCreateReqDto = TodoCreateReqDto.builder()
			.todoDate(LocalDateTime.of(2023,11,1,13,30))
			.statType(StatType.생존력)
			.build();

		// when, then
		mockMvc.perform(post("/todo/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todoCreateReqDto))
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
		;
	}

	@Test
	@DisplayName("Todo 등록 api 요청시 오늘보다 이전 날짜에 등록 요청이 온 경우 에러 반환 테스트")
	public void When_InsertWrongInput_Expect_BadRequest() throws Exception {
		// given
		TodoCreateReqDto todoCreateReqDto = TodoCreateReqDto.builder()
			.todoDate(LocalDateTime.of(2023,10,1,13,30))
			.content("코딩테스트")
			.statType(StatType.생존력)
			.build();

		// when, then
		mockMvc.perform(post("/todo/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todoCreateReqDto))
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errorCode").value("T-001"))
			.andExpect(jsonPath("errorMessage").value("날짜가 유효하지 않습니다."))
		;
	}

	@Test
	@DisplayName("Todo 리스트 조회 테스트")
	public void When_FindTodoList_Expect_IsOk() throws Exception {
		// given
		String todoDate = "2023-11-01";

		// when, then
		mockMvc.perform(get("/todo/")
				.contentType(MediaType.APPLICATION_JSON)
				.param("date",todoDate)
			)
			.andDo(print())
			.andExpect(status().isOk())
		;
	}

	@Test
	@DisplayName("Todo 완료 혹은 미완료 요청 테스트")
	public void When_ModifyTodoCheck_Expect_IsOk() throws Exception {
		// given
		Todo todo = createTodo("일찍 일어나기",StatType.갓생력, LocalDateTime.now());
		todoRepository.save(todo);

		// when, then
		mockMvc.perform(patch("/todo/check/{todoId}", todo.getId())
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
		;
	}

	@Test
	@DisplayName("Todo 완료 혹은 미완료 요청 실패 테스트")
	public void When_ModifyTodoCheck_Expect_BadRequest() throws Exception {
		// given
		Long todoId = 123L;

		// when, then
		mockMvc.perform(patch("/todo/check/{todoId}", todoId)
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errorCode").value("T-002"))
			.andExpect(jsonPath("errorMessage").value("해당 체크리스트가 존재하지 않습니다."))
		;
	}

	@Test
	@DisplayName("Todo 삭제 요청 테스트")
	public void When_RemoveTodo_Expect_IsOk() throws Exception {
		// given
		Todo todo = createTodo("일찍 일어나기",StatType.갓생력, LocalDateTime.now());
		todoRepository.save(todo);

		// when, then
		mockMvc.perform(patch("/todo/remove/{todoId}", todo.getId())
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
		;
	}

	@Test
	@DisplayName("오늘 이전의 Todo 삭제 요청시 실패 테스트")
	public void When_RemoveTodoBeforeToday_Expect_BadRequest() throws Exception {
		// given
		Todo todo = createTodo("일찍 일어나기",StatType.갓생력, LocalDateTime.now().minusDays(1));
		todoRepository.save(todo);

		// when, then
		mockMvc.perform(patch("/todo/remove/{todoId}", todo.getId())
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errorCode").value("T-001"))
			.andExpect(jsonPath("errorMessage").value("날짜가 유효하지 않습니다."))
		;
	}

	@Test
	@DisplayName("Todo 수정 요청 테스트")
	public void When_ModifyTodo_Expect_IsOk() throws Exception {
		// given
		Todo todo = createTodo("헬스가기",StatType.튼튼력, LocalDateTime.now());
		todoRepository.save(todo);
		TodoModifyReqDto todoModifyReqDto = createTodoModifyReqDto(
			"산책하기", StatType.튼튼력, LocalDateTime.now().plusDays(1), true
		);

		// when, thdn
		mockMvc.perform(patch("/todo/{todoId}", todo.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todoModifyReqDto))
			)
			.andDo(print())
			.andExpect(status().isOk())
		;
	}

	@Test
	@DisplayName("Todo 오늘 이전의 날짜로 수정 요청 올 경우 BadRequest 테스트")
	public void When_ModifyTodoBeforeToday_Expect_BadRequest() throws Exception {
		// given
		Todo todo = createTodo("헬스가기",StatType.튼튼력, LocalDateTime.now());
		todoRepository.save(todo);
		TodoModifyReqDto todoModifyReqDto = createTodoModifyReqDto(
			"산책하기", StatType.튼튼력, LocalDateTime.now().minusDays(1), true
		);

		// when, thdn
		mockMvc.perform(patch("/todo/{todoId}", todo.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todoModifyReqDto))
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errorCode").value("T-001"))
			.andExpect(jsonPath("errorMessage").value("날짜가 유효하지 않습니다."))
		;
	}

	@Test
	@DisplayName("Todo 수정 시 내용에 빈 값이 들어올 경우 테스트")
	public void When_ModifyTodoEmptyContent_Expect_BadRequest() throws Exception {
		// given
		Todo todo = createTodo("헬스가기",StatType.튼튼력, LocalDateTime.now());
		todoRepository.save(todo);
		TodoModifyReqDto todoModifyReqDto = createTodoModifyReqDto(
			"", StatType.튼튼력, LocalDateTime.now().plusDays(1), false
		);

		// when, thdn
		mockMvc.perform(patch("/todo/{todoId}", todo.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todoModifyReqDto))
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
		;
	}

	@Test
	@DisplayName("Todo 수정 시 날짜에 빈 값이 들어올 경우 테스트")
	public void When_ModifyTodoEmptyTodoDate_Expect_BadRequest() throws Exception {
		// given
		Todo todo = createTodo("헬스가기",StatType.튼튼력, LocalDateTime.now());
		todoRepository.save(todo);
		TodoModifyReqDto todoModifyReqDto = createTodoModifyReqDto(
			"산책하기", StatType.튼튼력, null, false
		);

		// when, thdn
		mockMvc.perform(patch("/todo/{todoId}", todo.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todoModifyReqDto))
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
		;
	}

	@Test
	@DisplayName("Todo 수정 시 알람에 빈 값이 들어올 경우 테스트")
	public void When_ModifyTodoEmptyAlarm_Expect_BadRequest() throws Exception {
		// given
		Todo todo = createTodo("헬스가기",StatType.튼튼력, LocalDateTime.now());
		todoRepository.save(todo);
		TodoModifyReqDto todoModifyReqDto = createTodoModifyReqDto(
			"산책하기", StatType.튼튼력, LocalDateTime.now().plusDays(1), null
		);

		// when, thdn
		mockMvc.perform(patch("/todo/{todoId}", todo.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todoModifyReqDto))
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
		;
	}

	@Test
	@DisplayName("Todo 수정 시 스탯타입에 빈 값이 들어올 경우 테스트")
	public void When_ModifyTodoEmptyStatType_Expect_BadRequest() throws Exception {
		// given
		Todo todo = createTodo("헬스가기",StatType.튼튼력, LocalDateTime.now());
		todoRepository.save(todo);
		TodoModifyReqDto todoModifyReqDto = createTodoModifyReqDto(
			"산책하기", null, LocalDateTime.now().plusDays(1), true
		);

		// when, thdn
		mockMvc.perform(patch("/todo/{todoId}", todo.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todoModifyReqDto))
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
		;
	}

	@Test
	@DisplayName("년도와 월이 현재 날짜의 달이 아닐 경우 완료한 투두, 루틴 비율 보내주는 요청 테스트")
	public void When_FindTodoRoutineRatioByMonth_Expect_IsOk() throws Exception {
		// given, when, thdn
		mockMvc.perform(get("/todo/done")
				.contentType(MediaType.APPLICATION_JSON)
				.param("yearMonth", "2023-10")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].date").value("2023-10-01"))
			.andExpect(jsonPath("$[30].date").value("2023-10-31"))

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
