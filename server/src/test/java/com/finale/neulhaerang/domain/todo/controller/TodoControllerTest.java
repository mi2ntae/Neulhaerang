package com.finale.neulhaerang.domain.todo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.todo.dto.request.TodoCreateReqDto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TodoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("Todo 등록 테스트")
	public void When_InsertTodo_Expect_IsOk() throws Exception {
		// given
		TodoCreateReqDto todoRequestDto = TodoCreateReqDto.builder()
			.todoDate(LocalDateTime.of(2023,11,1,13,30))
			.content("코딩테스트")
			.statType(StatType.생존력)
			.build();

		// when, then
		mockMvc.perform(post("/todo/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todoRequestDto))
			)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(content().string("Create Success"))
		;
	}

	@Test
	@DisplayName("Todo 등록 시 빈 값이 들어올 경우 에러 발생 테스트")
	public void When_InsertEmptyInput_Expect_BadRequest() throws Exception {
		// given
		TodoCreateReqDto todoCreateReqDto = TodoCreateReqDto.builder().build();

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
	@DisplayName("Todo 등록 시 잘못된 입력값이 등록된 경우 에러 발생 테스트")
	public void When_InsertWrongInput_Expect_BadRequest() throws Exception{
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
		;
	}
}
