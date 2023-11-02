package com.finale.neulhaerang.domain.member.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.dto.response.MemberStatusResDto;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.global.exception.member.NotExistMemberException;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceImplTest {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRepository memberRepository;

	@Test
	@DisplayName("회원 상태 정보를 조회할 경우 MemberStatusResDto 형식으로 결과를 반환한다.")
	void When_FindMemberStatus_Expect_MemberStatusDto() {
		// given when
		Member member = memberRepository.save(createMember());

		// then
		assertThat(memberService.findStatusByMemberId(member.getId())).isInstanceOf(MemberStatusResDto.class);
	}

	@Test
	@DisplayName("회원 상태 정보를 조회할 경우 MemberStatusResDto 형식으로 결과를 반환한다.")
	void When_FindNotExistMemberStatus_Expect_ThrowException() {
		// given when
		Member member = memberRepository.save(createMember());

		// when
		long memberId = member.getId()-1; // 0

		// then
		assertThatThrownBy(() -> memberService.findStatusByMemberId(memberId)).isInstanceOf(NotExistMemberException.class);
	}

	@Test
	void findCharacterByMemberId() {
	}


	@Test
	void loadMemberByDeviceToken() {
	}

	@Test
	@DisplayName("회원 탈퇴를 진행할 경우, Member의 withdrawalDate에 값이 설정된다.")
	void When_RemoveMember_Expect_WithdrawalDateIsNotNull() {
		// given
		Member member = memberRepository.save(createMember());

		// when
		memberService.removeMember();

		// then
		assertThat(member.getWithdrawalDate()).isNotNull();
	}

	@Test
	@DisplayName("회원 탈퇴를 진행할 경우, 존재하지 않는 멤버를 삭제하면 에러가 난다.")
	void When_RemoveNotExistMember_Expect_ThrowException() {
		// given
		Member member = memberRepository.save(createMember());

		// when
		memberRepository.delete(member);

		// then
		assertThatThrownBy(() -> memberService.removeMember()).isInstanceOf(NotExistMemberException.class);
	}

	private static Member createMember() {
		return Member.builder()
			.kakaoId(11111111)
			.nickname("김청조").build();
	}
}