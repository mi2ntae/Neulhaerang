package com.finale.neulhaerang.domain.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.dto.response.MemberCharacterResDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberStatusResDto;
import com.finale.neulhaerang.domain.member.dto.response.StatListResDto;
import com.finale.neulhaerang.domain.member.entity.CharacterInfo;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.CharacterInfoRepository;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.global.exception.member.NotExistCharacterInfoException;
import com.finale.neulhaerang.global.exception.member.NotExistMemberException;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceImplTest {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private CharacterInfoRepository characterInfoRepository;

	private static Member testMember;

	@BeforeAll
	static void createTestMember(@Autowired MemberRepository memberRepository) {
		testMember = memberRepository.save(createMember());
	}

	@Test
	@DisplayName("회원 상태 정보를 조회할 경우, MemberStatusResDto 형식으로 결과를 반환한다.")
	void When_FindMemberStatus_Expect_MemberStatusDto() {
		// given when
		Member member = this.testMember;

		// then
		assertThat(memberService.findStatusByMemberId(member.getId())).isInstanceOf(MemberStatusResDto.class);
	}

	@Test
	@DisplayName("존재하지 않는 회원 상태 정보를 조회할 경우, 예외를 발생시킨다.")
	void When_FindNotExistMemberStatus_Expect_ThrowException() {
		// given
		Member member = this.testMember;

		// when
		long memberId = member.getId()-1; // 0

		// then
		assertThatThrownBy(() -> memberService.findStatusByMemberId(memberId)).isInstanceOf(NotExistMemberException.class);
	}

	@Test
	@DisplayName("회원 캐릭터 정보를 조회할 경우, MemberCharacterResDto 형식으로 결과를 반환하고 저장된 캐릭터 정보를 가진다.")
	void When_FindMemberCharacterInfo_Expect_MemberCharacterDto() {
		// given
		Member member = this.testMember;
		CharacterInfo characterInfo = characterInfoRepository.save(createCharacterInfo(member));

		// when
		MemberCharacterResDto memberCharacterResDto = MemberCharacterResDto.from(characterInfo);

		// then
		assertSoftly(s -> {
			s.assertThat(memberService.findCharacterByMemberId(member.getId())).isInstanceOf(MemberCharacterResDto.class);
			s.assertThat(memberService.findCharacterByMemberId(member.getId())).usingRecursiveComparison().isEqualTo(memberCharacterResDto);
		});
	}

	@Test
	@DisplayName("캐릭터 정보가 등록되지 않은 회원의 캐릭터 정보를 조회할 경우, 예외를 발생시킨다.")
	void When_FindMemberNotExistCharacterInfo_Expect_ThrowException() {
		// given when
		Member member = this.testMember;

		// then
		assertThatThrownBy(() -> memberService.findCharacterByMemberId(member.getId())).isInstanceOf(
			NotExistCharacterInfoException.class);
	}

	@Test
	void loadMemberByDeviceToken() {
	}

	@Test
	@DisplayName("회원 탈퇴를 진행할 경우, Member의 withdrawalDate에 값이 설정된다.")
	void When_RemoveMember_Expect_WithdrawalDateIsNotNull() {
		// given
		Long memberId = this.testMember.getId();

		// when
		memberService.removeMember();

		// then
		assertThat(memberRepository.findById(memberId).get().getWithdrawalDate()).isNotNull();
	}

	@Test
	@DisplayName("회원 탈퇴를 진행할 경우, 존재하지 않는 멤버를 삭제하면 예외를 발생시킨다.")
	void When_RemoveNotExistMember_Expect_ThrowException() {
		// given
		Member member = this.testMember;

		// when
		memberRepository.delete(member);

		// then
		assertThatThrownBy(() -> memberService.removeMember()).isInstanceOf(NotExistMemberException.class);
	}

	@Test
	@DisplayName("회원 스탯 전체 조회를 진행할 경우, List<StatListResDto> 형식으로 결과를 반환하며 길이가 6이다.")
	void When_FindStats_Expect_StatListResDto() {
		// given when
		Member member = this.testMember;

		// then
		assertSoftly(s -> {
			s.assertThat(memberService.findAllStatsByMemberId(member.getId())).hasSize(6);
			s.assertThat(memberService.findAllStatsByMemberId(member.getId()).get(0)).isInstanceOf(StatListResDto.class);
		});
	}

	@Test
	@DisplayName("회원 스탯 전체 조회를 진행할 경우, 존재하지 않는 멤버의 스탯을 조회하면 예외를 발생시킨다.")
	void When_FindStatsNotExistMember_Expect_ThrowException() {
		// given
		Member member = this.testMember;

		// when
		long memberId = member.getId()-1;

		// then
		assertThatThrownBy(() -> memberService.findAllStatsByMemberId(memberId)).isInstanceOf(NotExistMemberException.class);
	}

	private static Member createMember() {
		return Member.builder()
			.kakaoId(11111111)
			.nickname("김청조").build();
	}

	private static CharacterInfo createCharacterInfo(Member member) {
		return CharacterInfo.builder()
			.member(member)
			.hat("hat")
			.scarf("scarf")
			.backpack("backpack")
			.face("face")
			.glasses("glasses")
			.skin("skin")
			.hand("hand").build();
	}
}