package com.finale.neulhaerang.domain.title.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finale.neulhaerang.domain.title.dto.response.EarnedTitleResDto;
import com.finale.neulhaerang.domain.title.service.TitleService;
import com.finale.neulhaerang.global.util.BaseTest;

@AutoConfigureMockMvc
@WithMockUser(password = "1")
class TitleControllerTest extends BaseTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TitleService titleService;

	@DisplayName("얻은 칭호를 조회합니다. 칭호는 리스트 형태입니다.")
	@Test
	void When_FindEarnedTitle_Expect_TitleList() throws Exception {
		// given
		List<EarnedTitleResDto> result = List.of();
		when(titleService.findEarnedTitleByMember()).thenReturn(result);

		// when // then
		mockMvc.perform(
				get("/title")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@DisplayName("장착 칭호를 변경합니다.")
	@Test
	void When_ModifyEquipTitle_Expect_ChangeEquipTitle() throws Exception {
		// given // when // then
		mockMvc.perform(
				patch("/title/1")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

}