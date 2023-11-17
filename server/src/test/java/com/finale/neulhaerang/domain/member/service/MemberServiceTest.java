package com.finale.neulhaerang.domain.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.member.dto.request.CharacterModifyReqDto;
import com.finale.neulhaerang.domain.member.dto.request.StatRecordReqDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberCharacterResDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberProfileResDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberStatusResDto;
import com.finale.neulhaerang.domain.member.dto.response.StatListResDto;
import com.finale.neulhaerang.domain.member.dto.response.StatRecordListResDto;
import com.finale.neulhaerang.domain.member.entity.CharacterInfo;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.entity.Title;
import com.finale.neulhaerang.domain.title.repository.EarnedTitleRepository;
import com.finale.neulhaerang.domain.title.repository.TitleRepository;
import com.finale.neulhaerang.global.exception.common.InValidPageIndexException;
import com.finale.neulhaerang.global.exception.member.InvalidStatKindException;
import com.finale.neulhaerang.global.exception.member.NotExistCharacterInfoException;
import com.finale.neulhaerang.global.exception.member.NotExistMemberException;
import com.finale.neulhaerang.global.exception.title.NotEarnedTitleException;
import com.finale.neulhaerang.global.exception.title.NotExistTitleException;
import com.finale.neulhaerang.global.util.BaseTest;

class MemberServiceTest extends BaseTest {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private EarnedTitleRepository earnedTitleRepository;

	@DisplayName("회원 캐릭터 정보를 변경한다.")
	@Test
	void When_ModifyCharacterInfo_Expect_ChangeCharacterInfo() {
		// given
		Title title = Title.builder()
			.content("test")
			.name("test")
			.build();
		Title save = titleRepository.save(title);
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.hat(3)
			.backpack(3)
			.glasses(4)
			.scarf(2)
			.skin(1)
			.hand(1)
			.title(save.getId())
			.build();
		// when
		memberService.modifyCharacterInfoByMember(characterModifyReqDto);

		// then
		Optional<CharacterInfo> characterInfo = characterInfoRepository.findCharacterInfoByMember_Id(
			member.getId());
		assertThat(characterInfo.get().getScarf()).isEqualTo(2);
		assertThat(characterInfo.get().getHat()).isEqualTo(3);
		assertThat(characterInfo.get().getGlasses()).isEqualTo(4);
		assertThat(characterInfo.get().getBackpack()).isEqualTo(3);
		assertThat(characterInfo.get().getTitle()).isEqualTo(save.getId());
		assertThat(characterInfo.get().getSkin()).isEqualTo(1);
		assertThat(characterInfo.get().getHand()).isEqualTo(1);
	}

	@Test
	@DisplayName("회원 상태 정보를 조회할 경우, MemberStatusResDto 형식으로 결과를 반환한다.")
	void When_FindMemberStatus_Expect_MemberStatusDto() {
		// given when

		// then
		assertThat(memberService.findStatusByMemberId(member.getId())).isInstanceOf(MemberStatusResDto.class);
	}

	@Test
	@DisplayName("존재하지 않는 회원 상태 정보를 조회할 경우, 예외를 발생시킨다.")
	void When_FindNotExistMemberStatus_Expect_ThrowException() {
		// given
		long memberId = member.getId() - 1; // 0

		// when then
		assertThatThrownBy(() -> memberService.findStatusByMemberId(memberId)).isInstanceOf(
			NotExistMemberException.class);
	}

	@Test
	@DisplayName("회원 캐릭터 정보를 조회할 경우, MemberCharacterResDto 형식으로 결과를 반환하고 저장된 캐릭터 정보를 가진다.")
	void When_FindMemberCharacterInfo_Expect_MemberCharacterDto() {
		// given // when
		MemberCharacterResDto memberCharacterResDto = MemberCharacterResDto.from(characterInfo);

		// then
		assertSoftly(s -> {
			s.assertThat(memberService.findCharacterByMemberId(member.getId()))
				.isInstanceOf(MemberCharacterResDto.class);
			s.assertThat(memberService.findCharacterByMemberId(member.getId()))
				.usingRecursiveComparison()
				.isEqualTo(memberCharacterResDto);
		});
	}

	@DisplayName("장착 칭호를 변경합니다.")
	@Test
	void When_ModifyEquipTitle_Expect_ChangeEquipTitle() {
		// given
		Title title1 = createTitle(1L, "슬늘생2", "슬늘2");
		Title title = titleRepository.save(title1);
		EarnedTitle earnedTitle1 = createEarnedTitle(title);
		earnedTitleRepository.saveAll(List.of(earnedTitle1));
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.hat(3)
			.backpack(3)
			.glasses(4)
			.scarf(2)
			.title(1)
			.build();

		// when
		memberService.modifyCharacterInfoByMember(characterModifyReqDto);

		// then
		Optional<CharacterInfo> characterInfo = characterInfoRepository.findCharacterInfoByMember_Id(member.getId());
		assertThat(characterInfo.get().getTitle()).isEqualTo(1);
	}

	@DisplayName("장착 칭호 변경 시, 해당 칭호가 없다면 에러가 납니다.")
	@Test
	void When_ModifyEquipTitleWithNotExistTitle_Expect_NotExistTitleException() {
		// given
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.hat(3)
			.backpack(3)
			.glasses(4)
			.scarf(2)
			.title(1)
			.build();

		// when // then
		assertThatThrownBy(() -> memberService.modifyCharacterInfoByMember(characterModifyReqDto))
			.isInstanceOf(NotExistTitleException.class);
	}

	@DisplayName("장착 칭호 변경 시, 아직 얻지 못한 칭호라면 에러가 납니다.")
	@Test
	void When_ModifyEquipTitleWithNotEarnedTitle_Expect_NotEarnedTitleException() {
		// given
		Title title = createTitle(1L, "슬늘생1", "슬늘1");
		Title save = titleRepository.save(title);
		CharacterModifyReqDto characterModifyReqDto = CharacterModifyReqDto.builder()
			.hat(3)
			.backpack(3)
			.glasses(4)
			.scarf(2)
			.title(1)
			.build();

		// when // then
		assertThatThrownBy(() -> memberService.modifyCharacterInfoByMember(characterModifyReqDto))
			.isInstanceOf(NotEarnedTitleException.class);
	}

	@Test
	@DisplayName("캐릭터 정보가 등록되지 않은 회원의 캐릭터 정보를 조회할 경우, 예외를 발생시킨다.")
	void When_FindMemberNotExistCharacterInfo_Expect_ThrowException() {
		// given

		// when then
		assertThatThrownBy(() -> memberService.findCharacterByMemberId(member.getId())).isInstanceOf(
			NotExistCharacterInfoException.class);
	}

	@Test
	@DisplayName("회원 탈퇴를 진행할 경우, Member의 withdrawalDate에 값이 설정된다.")
	void When_RemoveMember_Expect_WithdrawalDateIsNotNull() {
		// given when
		memberService.removeMember();

		// then
		assertThat(memberRepository.findById(member.getId()).get().getWithdrawalDate()).isNotNull();
	}

	@Test
	@DisplayName("회원 탈퇴를 진행할 경우, 존재하지 않는 멤버를 삭제하면 예외를 발생시킨다.")
	void When_RemoveNotExistMember_Expect_ThrowException() {
		// given when
		memberRepository.delete(member);

		// then
		assertThatThrownBy(() -> memberService.removeMember()).isInstanceOf(NotExistMemberException.class);
	}

	@Test
	@DisplayName("회원 스탯 전체 조회를 진행할 경우, List<StatListResDto> 형식으로 결과를 반환하며 길이가 6이다.")
	void When_FindStats_Expect_StatListResDto() {
		// given

		// when then
		assertSoftly(s -> {
			s.assertThat(memberService.findAllStatsByMemberId(member.getId())).hasSize(6);
			s.assertThat(memberService.findAllStatsByMemberId(member.getId()).get(0))
				.isInstanceOf(StatListResDto.class);
		});
	}

	@Test
	@DisplayName("회원 스탯 전체 조회를 진행할 경우, 존재하지 않는 멤버의 스탯을 조회하면 예외를 발생시킨다.")
	void When_FindStatsNotExistMember_Expect_ThrowException() {
		// given when
		long memberId = member.getId() - 1;    // 0

		// then
		assertThatThrownBy(() -> memberService.findAllStatsByMemberId(memberId)).isInstanceOf(
			NotExistMemberException.class);
	}

	@Test
	@DisplayName("회원 스탯 전체 조회를 진행할 경우, 모든 스탯이 0인 경우에도 조회가 가능해야 한다.")
	void When_FindStatsIfZero_Expect_StatListResDto() {
		// given
		memberService.createStat(StatRecordReqDto.builder()
			.statType(StatType.갓생력)
			.recordedDate(LocalDateTime.now())
			.reason("a")
			.weight(0).build()
		);

		// when then
		assertSoftly(s -> {
			s.assertThat(memberService.findAllStatsByMemberId(member.getId())).hasSize(6);
			s.assertThat(memberService.findAllStatsByMemberId(member.getId()).get(0))
				.isInstanceOf(StatListResDto.class);
		});
	}

	@Test
	@DisplayName("회원 특정 스탯의 변경 추세를 조회할 경우, 오늘 날짜로부터 특정일 전까지 해당 스탯이 얼마였는지 특정 길이의 배열로 결과를 반환한다.")
	void When_FindStatChangeRecord_Expect_StatChangeRecordResDto() {
		// given
		int statNo = 0;    // 갓생력
		int numberOfStatFindDay = 7;    // 7일 전까지

		// when then
		assertSoftly(s -> {
			s.assertThat(memberService.findStatChangeRecordLastDaysByStatType(statNo)).isInstanceOf(int[].class);
			s.assertThat(memberService.findStatChangeRecordLastDaysByStatType(statNo)).hasSize(numberOfStatFindDay);
		});
	}

	@Test
	@DisplayName("올바르지 않은 종류의 스탯 변경 추세를 조회할 경우, 예외를 발생시킨다.")
	void When_FindStatChangeRecordInvalidStatType_Expect_ThrowException() {
		// given
		int statNo = -1;    // 올바르지 않은 스탯 종류

		// when then
		assertThatThrownBy(() -> memberService.findStatChangeRecordLastDaysByStatType(statNo)).isInstanceOf(
			InvalidStatKindException.class);
	}

	@Test
	@DisplayName("회원의 특정 스탯에 대한 변경 내역을 페이지 번호로 조회할 때, 해당 스탯 생성 내역을 리스트로 반환한다.")
	void When_FindStatChangeRecordListByStatTypeAndPage_Expect_StatRecordListResDto() {
		// given
		int statNo = 0;    // 갓생력
		int page = 0;
		memberService.createStat(StatRecordReqDto.builder()
			.statType(StatType.갓생력)
			.recordedDate(LocalDateTime.now())
			.reason("a")
			.weight(0).build()
		);

		// when then
		assertSoftly(s -> {
			s.assertThat(memberService.findStatChangeRecordByStatType(statNo, page)).isInstanceOf(List.class);
			s.assertThat(memberService.findStatChangeRecordByStatType(statNo, page).get(0))
				.isInstanceOf(StatRecordListResDto.class);
		});
	}

	@Test
	@DisplayName("올바르지 않은 종류의 스탯의 변경 내역을 조회할 경우, 예외를 발생시킨다.")
	void When_FindStatChangeRecordListInvalidStatType_Expect_ThrowException() {
		// given
		int statNo = -1;    // 올바르지 않은 스탯 종류
		int page = 0;

		// when then
		assertThatThrownBy(() -> memberService.findStatChangeRecordByStatType(statNo, page)).isInstanceOf(
			InvalidStatKindException.class);
	}

	@Test
	@DisplayName("올바르지 않은 페이지 번호로 스탯의 변경 내역을 조회할 경우, 예외를 발생시킨다.")
	void When_FindStatChangeRecordListInvalidPage_Expect_ThrowException() {
		// given
		int statNo = 0;
		int page = -1;

		// when then
		assertThatThrownBy(() -> memberService.findStatChangeRecordByStatType(statNo, page)).isInstanceOf(
			InValidPageIndexException.class);
	}

	@Test
	@DisplayName("회원의 아이디로 프로필 정보를 조회할 경우, MemberProfileResDto 형식으로 결과를 반환한다")
	void When_FindMemberProfileByMemberId_Expect_MemberProfileResDto() {

		// when then
		assertThat(memberService.findMemberProfileByMemberId(member.getId())).isInstanceOf(MemberProfileResDto.class);
	}

	@Test
	@DisplayName("회원의 아이디로 프로필 정보를 조회할 경우, 존재하지 않는 멤버의 스탯을 조회하면 예외를 발생시킨다.")
	void When_FindMemberProfileByNotExistMemberId_Expect_ThrowException() {
		// given
		int memberId = -1;

		// when then
		assertThatThrownBy(() -> memberService.findMemberProfileByMemberId(memberId)).isInstanceOf(
			NotExistMemberException.class);
	}

	private static CharacterInfo createCharacterInfo(Member member) {
		return CharacterInfo.builder()
			.member(member)
			.backpack(0)
			.glasses(0)
			.scarf(0)
			.hat(0)
			.build();
	}

	private Title createTitle(Long id, String name, String content) {
		return Title.builder()
			.id(id)
			.name(name)
			.content(content)
			.build();
	}

	private EarnedTitle createEarnedTitle(Title title) {
		return EarnedTitle.builder()
			.member(member)
			.title(title)
			.build();
	}
}