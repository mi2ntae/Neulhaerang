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
import com.finale.neulhaerang.global.util.BaseTest;
import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;

@AutoConfigureMockMvc
@WithMockUser(password = "1")
class TodoControllerTest extends BaseTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

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
			.andExpect(jsonPath("errorMessage").value("등록 날짜가 유효하지 않습니다."))
		;
	}
}
