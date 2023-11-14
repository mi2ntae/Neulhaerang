package com.finale.neulhaerang.domain.member.entity;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeviceTest {
	@DisplayName("Device Entity Build Test")
	@Test
	void When_EntityBuilder_Expect_isNotNull() {
		// given
		Member member = Member.builder()
			.kakaoId(1L)
			.build();
		// when
		Device device = createDevice(member, "1234");

		// then
		assertThat(device).isNotNull();
	}

	@DisplayName("Device Entity Bean 주입 Test")
	@Test
	void When_DeviceDI_Expect_isEqualTo() {
		// given
		Member member = Member.builder()
			.kakaoId(1L)
			.build();
		String token = "1234";
		// when
		Device device = createDevice(member, token);

		// then
		assertSoftly(s -> {
			s.assertThat(device.getDeviceToken()).isEqualTo(token);
			s.assertThat(device.getMember().getKakaoId()).isEqualTo(member.getKakaoId());
		});
	}

	@DisplayName("디바이스를 생성합니다.")
	@Test
	void When_CreateDevice_Expect_IsCreate() {
		// given
		Member member = Member.builder()
			.kakaoId(1L)
			.build();
		String token = "1234";

		// when
		Device device = Device.create(member, token);

		// then
		assertSoftly(s -> {
			s.assertThat(device.getDeviceToken()).isEqualTo(token);
			s.assertThat(device.getMember().getKakaoId()).isEqualTo(member.getKakaoId());
		});
	}

	Device createDevice(Member member, String token) {
		return Device.builder()
			.member(member)
			.deviceToken(token)
			.build();
	}
}