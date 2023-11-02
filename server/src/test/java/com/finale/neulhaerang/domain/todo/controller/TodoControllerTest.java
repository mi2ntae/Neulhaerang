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
		;
	}

	@Test
	@DisplayName("Todo 삭제 요청 테스트")
	public void When_RemoveTodo_Expect_BadRequest() throws Exception {
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
	public void When_RemoveTodoBeforeToday_Expect_IsOk() throws Exception {
		// given
		Todo todo = createTodo("일찍 일어나기",StatType.갓생력, LocalDateTime.now().minusDays(1));
		todoRepository.save(todo);

		// when, then
		mockMvc.perform(patch("/todo/remove/{todoId}", todo.getId())
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
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
}
