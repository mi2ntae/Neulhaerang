package com.finale.neulhaerang.domain.routine.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.routine.service.RoutineService;

@WebMvcTest(controllers = RoutineController.class)
class RoutineControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private RoutineService routineService;

	@DisplayName("새로운 루틴을 생성합니다.")
	@Test
	@WithUserDetails
	void When_CreateRoutine_Expect_isCreate() throws Exception {
		// given
		RoutineCreateReqDto routineCreateReqDto = createRoutine("아침밥 챙겨랏 S2", true, LocalTime.of(8, 30, 0),
			List.of(true, true, true, false, false, false, false), StatType.생존력);

		// when // then
		mockMvc.perform(post("/routine").with(csrf())
				.content(objectMapper.writeValueAsString(routineCreateReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@DisplayName("새로운 루틴 생성 시, StatType는 필수 입니다.")
	@Test
	@WithUserDetails
	void When_CreateRoutineWithoutStatType_Expect_isCreate() throws Exception {
		// given
		RoutineCreateReqDto routineCreateReqDto = RoutineCreateReqDto.builder()
			.content("아침밥 먹고 가기")
			.repeated(List.of(true, true, true, true, true, true, true))
			.alarm(true)
			.alarmTime(LocalTime.of(8, 30, 0))
			.build();

		// when // then
		mockMvc.perform(post("/routine").with(csrf())
				.content(objectMapper.writeValueAsString(routineCreateReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("새로운 루틴 생성 시, Content는 필수 입니다.")
	@Test
	@WithUserDetails
	void When_CreateRoutineWithoutContent_Expect_isCreate() throws Exception {
		// given
		RoutineCreateReqDto routineCreateReqDto = RoutineCreateReqDto.builder()
			.repeated(List.of(true, true, true, true, true, true, true))
			.alarm(true)
			.alarmTime(LocalTime.of(8, 30, 0))
			.statType(StatType.생존력)
			.build();

		// when // then
		mockMvc.perform(post("/routine").with(csrf())
				.content(objectMapper.writeValueAsString(routineCreateReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("새로운 루틴 생성 시, Repeated는 필수 입니다.")
	@Test
	@WithUserDetails
	void When_CreateRoutineWithoutRepeated_Expect_isCreate() throws Exception {
		// given
		RoutineCreateReqDto routineCreateReqDto = RoutineCreateReqDto.builder()
			.content("아침밥 먹고 가기")
			.alarm(true)
			.alarmTime(LocalTime.of(8, 30, 0))
			.statType(StatType.생존력)
			.build();

		// when // then
		mockMvc.perform(post("/routine").with(csrf())
				.content(objectMapper.writeValueAsString(routineCreateReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	private static RoutineCreateReqDto createRoutine(String content, boolean alarm, LocalTime alarmTime,
		List<Boolean> repeated, StatType statType) {
		return RoutineCreateReqDto.builder()
			.content(content)
			.alarm(alarm)
			.alarmTime(alarmTime)
			.repeated(repeated)
			.statType(statType)
			.build();
	}
}