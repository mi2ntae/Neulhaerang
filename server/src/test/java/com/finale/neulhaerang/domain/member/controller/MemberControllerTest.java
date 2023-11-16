package com.finale.neulhaerang.domain.member.controller;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finale.neulhaerang.domain.member.dto.request.CharacterModifyReqDto;
import com.finale.neulhaerang.global.util.BaseTest;

@AutoConfigureMockMvc
@WithMockUser(password = "1")
class MemberControllerTest extends BaseTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@DisplayName("캐릭터 정보를 수정합니다.")
	@Test
	void When_ModifyCharacterInfoWithLowest_Expect_IsOk() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.scarf(0)
			.glasses(0)
			.hat(0)
			.backpack(0)
			.skin(0)
			.hand(0)
			.title(0)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@DisplayName("캐릭터 정보를 수정합니다.")
	@Test
	void When_ModifyCharacterInfoWithHighest_Expect_IsOk() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.scarf(3)
			.glasses(4)
			.hat(4)
			.skin(15)
			.hand(8)
			.skin(1)
			.hand(1)
			.backpack(4)
			.title(0)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@DisplayName("캐릭터 정보를 수정하는 경우 목도리 값 3을 넘을 수 없습니다.")
	@Test
	void When_ModifyCharacterInfoWithMoreThan3Scarf_Expect_IsBadRequest() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.glasses(1)
			.scarf(4)
			.hat(1)
			.skin(1)
			.hand(1)
			.backpack(1)
			.title(31)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("캐릭터 정보를 수정하는 경우 목도리 값이 0보다 작을 수 없습니다.")
	@Test
	void When_ModifyCharacterInfoWithLowerThan0Scarf_Expect_IsBadRequest() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.glasses(1)
			.scarf(-1)
			.hat(1)
			.skin(1)
			.hand(1)
			.backpack(1)
			.title(31)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("캐릭터 정보를 수정하는 경우 가방 값은 8이상일 수 없습니다.")
	@Test
	void When_ModifyCharacterInfoWithMoreThan8BackPack_Expect_IsBadRequest() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.scarf(1)
			.glasses(1)
			.backpack(9)
			.hat(1)
			.skin(1)
			.hand(1)
			.title(31)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("캐릭터 정보를 수정하는 경우 가방 값은 0이하일 수 없습니다.")
	@Test
	void When_ModifyCharacterInfoWithLowerThan0BackPack_Expect_IsBadRequest() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.scarf(1)
			.glasses(1)
			.backpack(-1)
			.hat(1)
			.skin(1)
			.hand(1)
			.title(31)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("캐릭터 정보를 수정하는 경우 안경 값은 4이상일 수 없습니다.")
	@Test
	void When_ModifyCharacterInfoWithMoreThan4Glasses_Expect_IsBadRequest() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.scarf(1)
			.glasses(5)
			.backpack(1)
			.hat(1)
			.skin(1)
			.hand(1)
			.title(31)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("캐릭터 정보를 수정하는 경우 안경 값은 0이하일 수 없습니다.")
	@Test
	void When_ModifyCharacterInfoWithLowerThan0Glasses_Expect_IsBadRequest() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.scarf(1)
			.glasses(-1)
			.backpack(1)
			.hat(1)
			.skin(1)
			.hand(1)
			.title(31)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("캐릭터 정보를 수정하는 경우 모자 값은 4이상일 수 없습니다.")
	@Test
	void When_ModifyCharacterInfoWithMoreThan4Hat_Expect_IsBadRequest() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.scarf(1)
			.glasses(1)
			.backpack(1)
			.hat(5)
			.skin(1)
			.hand(1)
			.title(31)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("캐릭터 정보를 수정하는 경우 모자 값은 0이하일 수 없습니다.")
	@Test
	void When_ModifyCharacterInfoWithLowerThan0Hat_Expect_IsBadRequest() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.scarf(1)
			.glasses(1)
			.backpack(1)
			.hat(-1)
			.skin(1)
			.hand(1)
			.title(31)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("캐릭터 정보를 수정하는 경우 스킨 값은 15이상일 수 없습니다.")
	@Test
	void When_ModifyCharacterInfoWithMoreThan8Skin_Expect_IsBadRequest() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.scarf(1)
			.glasses(1)
			.backpack(1)
			.hat(4)
			.skin(16)
			.hand(1)
			.title(31)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("캐릭터 정보를 수정하는 경우 스킨 값은 0이하일 수 없습니다.")
	@Test
	void When_ModifyCharacterInfoWithLowerThan0Skin_Expect_IsBadRequest() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.scarf(1)
			.glasses(1)
			.backpack(1)
			.hat(1)
			.hand(1)
			.title(1)
			.skin(-1)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("캐릭터 정보를 수정하는 경우 무기 값은 8이상일 수 없습니다.")
	@Test
	void When_ModifyCharacterInfoWithMoreThan8Hank_Expect_IsBadRequest() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.scarf(1)
			.glasses(1)
			.backpack(1)
			.hat(4)
			.skin(1)
			.hand(9)
			.title(31)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("캐릭터 정보를 수정하는 경우 무기 값은 0이하일 수 없습니다.")
	@Test
	void When_ModifyCharacterInfoWithLowerThan0Hand_Expect_IsBadRequest() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.scarf(1)
			.glasses(1)
			.backpack(1)
			.hat(1)
			.hand(-1)
			.title(1)
			.skin(1)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("캐릭터 정보를 수정하는 경우 칭호 값은 31이상일 수 없습니다.")
	@Test
	void When_ModifyCharacterInfoWithMoreThan32Title_Expect_IsBadRequest() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.scarf(1)
			.glasses(1)
			.backpack(1)
			.hat(4)
			.skin(1)
			.hand(1)
			.title(32)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("캐릭터 정보를 수정하는 경우 칭호 값은 0이하일 수 없습니다.")
	@Test
	void When_ModifyCharacterInfoWithLowerThan0Title_Expect_IsBadRequest() throws Exception {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.scarf(1)
			.glasses(1)
			.backpack(1)
			.hat(1)
			.skin(1)
			.hand(1)
			.title(-1)
			.build();

		// when // then
		mockMvc.perform(patch("/member/character")
				.content(objectMapper.writeValueAsString(characterModifyReqDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}
}