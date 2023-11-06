package com.finale.neulhaerang.global.event.handler;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.member.document.StatRecord;
import com.finale.neulhaerang.domain.member.dto.request.StatRecordReqDto;
import com.finale.neulhaerang.domain.member.repository.MemberStatRepository;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.entity.Title;
import com.finale.neulhaerang.domain.title.repository.EarnedTitleRepository;
import com.finale.neulhaerang.domain.title.repository.TitleRepository;
import com.finale.neulhaerang.global.util.BaseTest;

class StatTitleEventHandlerTest extends BaseTest {
	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private EarnedTitleRepository earnedTitleRepository;
	@Autowired
	private MemberStatRepository memberStatRepository;
	@Autowired
	private StatTitleEventHandler statTitleEventHandler;

	@BeforeEach
	void tearDown() {
		memberStatRepository.deleteAll();
	}

	@DisplayName("갓생력의 stat 포인트가 90이라면, 갓생력 관련 모든 칭호가 발급됩니다.")
	@Test
	void When_LifeStatMoreThan90_Expect_GetAllOfLifeStatTitle() {
		// given
		Title title1 = createTitle(1L, "갓생C+", "갓C+");
		Title title2 = createTitle(2L, "갓생B+", "갓B+");
		Title title3 = createTitle(3L, "갓생A+", "갓A+");
		titleRepository.saveAll(List.of(title1, title2, title3));

		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("늘해랑 가입 환영", LocalDateTime.now().minusDays(20), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("멋쟁이 토마토", LocalDateTime.now().minusDays(17), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("오늘부터 갓생 산다.", LocalDateTime.now().minusDays(10), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("내가 최고라지요", LocalDateTime.now().minusDays(7), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("나야나", LocalDateTime.now().minusDays(1), StatType.갓생력, 10),
				member.getId()));

		// when
		statTitleEventHandler.checkIfGetTitleOrNot(member, StatType.갓생력);

		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(3);
	}

	@DisplayName("갓생력의 stat 포인트가 89이라면, 갓생력 관련 모든 칭호 2개가 발급됩니다.")
	@Test
	void When_LifeStat89Point_Expect_GetTwoOfLifeStatTitle() {
		// given
		Title title1 = createTitle(1L, "갓생C+", "갓C+");
		Title title2 = createTitle(2L, "갓생B+", "갓B+");
		Title title3 = createTitle(3L, "갓생A+", "갓A+");
		titleRepository.saveAll(List.of(title1, title2, title3));

		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("늘해랑 가입 환영", LocalDateTime.now().minusDays(20), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("멋쟁이 토마토", LocalDateTime.now().minusDays(17), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("오늘부터 갓생 산다.", LocalDateTime.now().minusDays(10), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("내가 최고라지요", LocalDateTime.now().minusDays(7), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("나야나", LocalDateTime.now().minusDays(1), StatType.갓생력, 9),
				member.getId()));

		// when
		statTitleEventHandler.checkIfGetTitleOrNot(member, StatType.갓생력);

		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(2);
	}

	@DisplayName("갓생력의 stat 포인트가 69이라면, 갓생력 관련 모든 칭호 2개가 발급됩니다.")
	@Test
	void When_LifeStat69Point_Expect_GetOneOfLifeStatTitle() {
		// given
		Title title1 = createTitle(1L, "갓생C+", "갓C+");
		Title title2 = createTitle(2L, "갓생B+", "갓B+");
		Title title3 = createTitle(3L, "갓생A+", "갓A+");
		titleRepository.saveAll(List.of(title1, title2, title3));

		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("늘해랑 가입 환영", LocalDateTime.now().minusDays(20), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("멋쟁이 토마토", LocalDateTime.now().minusDays(17), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("오늘부터 갓생 산다.", LocalDateTime.now().minusDays(10), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("나야나", LocalDateTime.now().minusDays(1), StatType.갓생력, 9),
				member.getId()));

		// when
		statTitleEventHandler.checkIfGetTitleOrNot(member, StatType.갓생력);

		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(1);
	}

	@DisplayName("갓생력의 stat 포인트가 49이라면, 갓생력 관련 모든 칭호 발급되지 않습니다.")
	@Test
	void When_LifeStat49Point_Expect_GetNothingLifeStatTitle() {
		// given
		Title title1 = createTitle(1L, "갓생C+", "갓C+");
		Title title2 = createTitle(2L, "갓생B+", "갓B+");
		Title title3 = createTitle(3L, "갓생A+", "갓A+");
		titleRepository.saveAll(List.of(title1, title2, title3));

		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("늘해랑 가입 환영", LocalDateTime.now().minusDays(20), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("멋쟁이 토마토", LocalDateTime.now().minusDays(17), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("나야나", LocalDateTime.now().minusDays(1), StatType.갓생력, 9),
				member.getId()));

		// when
		statTitleEventHandler.checkIfGetTitleOrNot(member, StatType.갓생력);

		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(0);
	}

	private static Title createTitle(Long id, String name, String content) {
		return Title.builder()
			.id(id)
			.name(name)
			.content(content)
			.build();
	}
}