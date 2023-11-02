package com.finale.neulhaerang.domain.routine.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.routine.service.RoutineService;
import com.finale.neulhaerang.global.util.BaseTest;

@AutoConfigureMockMvc
@WithMockUser(password = "1")
class RoutineControllerTest extends BaseTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private RoutineService routineService;

	@DisplayName("새로운 루틴을 생성합니다.")
	@Test
	void When_CreateRoutine_Expect_IsCreate() throws Exception {
		// given
		RoutineCreateReqDto routineCreateReqDto = createRoutine("아침밥 챙겨랏 S2", true, LocalTime.of(8, 30, 0),
			List.of(true, true, true, false, false, false, false), StatType.생존력);

		// when // then
		mockMvc.perform(post("/routine")
				.content(objectMapper.writeValueAsString(routineCreateReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isCreated());
	}

	@DisplayName("새로운 루틴 생성 시, StatType는 필수 입니다.")
	@Test
	void When_CreateRoutineWithoutStatType_Expect_IsBadRequest() throws Exception {
		// given
		RoutineCreateReqDto routineCreateReqDto = RoutineCreateReqDto.builder()
			.content("아침밥 먹고 가기")
			.repeated(List.of(true, true, true, true, true, true, true))
			.alarm(true)
			.alarmTime(LocalTime.of(8, 30, 0))
			.build();

		// when // then
		mockMvc.perform(post("/routine")
				.content(objectMapper.writeValueAsString(routineCreateReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("새로운 루틴 생성 시, Content는 필수 입니다.")
	@Test
	void When_CreateRoutineWithoutContent_Expect_IsBadRequest() throws Exception {
		// given
		RoutineCreateReqDto routineCreateReqDto = RoutineCreateReqDto.builder()
			.repeated(List.of(true, true, true, true, true, true, true))
			.alarm(true)
			.alarmTime(LocalTime.of(8, 30, 0))
			.statType(StatType.생존력)
			.build();

		// when // then
		mockMvc.perform(post("/routine")
				.content(objectMapper.writeValueAsString(routineCreateReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("새로운 루틴 생성 시, Repeated는 필수 입니다.")
	@Test
	void When_CreateRoutineWithoutRepeated_Expect_IsBadRequest() throws Exception {
		// given
		RoutineCreateReqDto routineCreateReqDto = RoutineCreateReqDto.builder()
			.content("아침밥 먹고 가기")
			.alarm(true)
			.alarmTime(LocalTime.of(8, 30, 0))
			.statType(StatType.생존력)
			.build();

		// when // then
		mockMvc.perform(post("/routine")
				.content(objectMapper.writeValueAsString(routineCreateReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("루틴을 조회합니다. 루틴은 List 형태여야 합니다.")
	@Test
	void When_FindRoutineAfterToday_Expect_RoutineList() throws Exception {
		// given
		when(routineService.findRoutineByMemberAndDate(LocalDate.now())).thenReturn(List.of());
		// when // then
		mockMvc.perform(
				get("/routine?date=2023-08-19")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@DisplayName("데일리 루틴의 체크 여부를 변경합니다.")
	@Test
	void When_ModifyRoutineCheck_Expect_IsOk() throws Exception {
		// given // when // then
		mockMvc.perform(
				patch("/routine/check/1")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	private RoutineCreateReqDto createRoutine(String content, boolean alarm, LocalTime alarmTime,
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