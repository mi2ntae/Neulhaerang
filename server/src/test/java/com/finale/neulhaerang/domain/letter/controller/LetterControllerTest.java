package com.finale.neulhaerang.domain.letter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.finale.neulhaerang.global.util.BaseTest;

@AutoConfigureMockMvc
@WithMockUser(password = "1")
class LetterControllerTest extends BaseTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("해당 날짜에 편지 불러오는 API TEST")
	public void When_FindLetter_Expect_Letter() throws Exception {
		// given
		String date = "2023-11-04";

		// when, then
		mockMvc.perform(get("/letter")
				.contentType(MediaType.APPLICATION_JSON)
				.param("date", date)
			)
			.andDo(print())
			.andExpect(status().isOk())
		;
	}
}