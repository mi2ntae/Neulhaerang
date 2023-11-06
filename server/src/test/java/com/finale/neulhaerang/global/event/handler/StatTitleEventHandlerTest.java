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
import com.finale.neulhaerang.global.exception.title.NotExistTitleException;
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

	@DisplayName("칭호 발급 시, 해당 칭호가 존재하지 않으면 에러가 납니다.")
	@Test
	void When_LifeStat50Point_Expect_GetNothingLifeStatTitle() {
		// given
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("늘해랑 가입 환영", LocalDateTime.now().minusDays(20), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("멋쟁이 토마토", LocalDateTime.now().minusDays(17), StatType.갓생력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("나야나", LocalDateTime.now().minusDays(1), StatType.갓생력, 10),
				member.getId()));

		// when // then
		assertThatThrownBy(() -> statTitleEventHandler.checkIfGetTitleOrNot(member, StatType.갓생력))
			.isInstanceOf(NotExistTitleException.class);
	}

	@DisplayName("생존력의 stat 포인트가 90이라면, 생존력 관련 모든 칭호가 발급됩니다.")
	@Test
	void When_SurvivalStatMoreThan90_Expect_GetAllOfSurvivalStatTitle() {
		// given
		Title title1 = createTitle(4L, "생존C+", "생C+");
		Title title2 = createTitle(5L, "생존B+", "생B+");
		Title title3 = createTitle(6L, "생존A+", "생A+");
		titleRepository.saveAll(List.of(title1, title2, title3));

		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("늘해랑 가입 환영", LocalDateTime.now().minusDays(20), StatType.생존력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("멋쟁이 토마토", LocalDateTime.now().minusDays(17), StatType.생존력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("오늘부터 갓생 산다.", LocalDateTime.now().minusDays(10), StatType.생존력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("내가 최고라지요", LocalDateTime.now().minusDays(7), StatType.생존력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("나야나", LocalDateTime.now().minusDays(1), StatType.생존력, 10),
				member.getId()));

		// when
		statTitleEventHandler.checkIfGetTitleOrNot(member, StatType.생존력);

		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(3);
	}

	@DisplayName("인싸력의 stat 포인트가 90이라면, 인싸력 관련 모든 칭호가 발급됩니다.")
	@Test
	void When_PopularityStatMoreThan90_Expect_GetAllOfPopularityStatTitle() {
		// given
		Title title1 = createTitle(7L, "인싸C+", "인C+");
		Title title2 = createTitle(8L, "인싸B+", "인B+");
		Title title3 = createTitle(9L, "인싸A+", "인A+");
		titleRepository.saveAll(List.of(title1, title2, title3));

		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("늘해랑 가입 환영", LocalDateTime.now().minusDays(20), StatType.인싸력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("멋쟁이 토마토", LocalDateTime.now().minusDays(17), StatType.인싸력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("오늘부터 갓생 산다.", LocalDateTime.now().minusDays(10), StatType.인싸력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("내가 최고라지요", LocalDateTime.now().minusDays(7), StatType.인싸력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("나야나", LocalDateTime.now().minusDays(1), StatType.인싸력, 10),
				member.getId()));

		// when
		statTitleEventHandler.checkIfGetTitleOrNot(member, StatType.인싸력);

		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(3);
	}

	@DisplayName("튼튼력의 stat 포인트가 90이라면, 튼튼력 관련 모든 칭호가 발급됩니다.")
	@Test
	void When_PowerStatMoreThan90_Expect_GetAllOfPowerStatTitle() {
		// given
		Title title1 = createTitle(10L, "튼튼C+", "튼C+");
		Title title2 = createTitle(11L, "튼튼B+", "튼B+");
		Title title3 = createTitle(12L, "튼튼A+", "튼A+");
		titleRepository.saveAll(List.of(title1, title2, title3));

		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("늘해랑 가입 환영", LocalDateTime.now().minusDays(20), StatType.튼튼력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("멋쟁이 토마토", LocalDateTime.now().minusDays(17), StatType.튼튼력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("오늘부터 갓생 산다.", LocalDateTime.now().minusDays(10), StatType.튼튼력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("내가 최고라지요", LocalDateTime.now().minusDays(7), StatType.튼튼력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("나야나", LocalDateTime.now().minusDays(1), StatType.튼튼력, 10),
				member.getId()));

		// when
		statTitleEventHandler.checkIfGetTitleOrNot(member, StatType.튼튼력);

		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(3);
	}

	@DisplayName("창의력의 stat 포인트가 90이라면, 창의력 관련 모든 칭호가 발급됩니다.")
	@Test
	void When_CreativeStatMoreThan90_Expect_GetAllOfCreativeStatTitle() {
		// given
		Title title1 = createTitle(13L, "갓생C+", "갓C+");
		Title title2 = createTitle(14L, "갓생B+", "갓B+");
		Title title3 = createTitle(15L, "갓생A+", "갓A+");
		titleRepository.saveAll(List.of(title1, title2, title3));

		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("늘해랑 가입 환영", LocalDateTime.now().minusDays(20), StatType.창의력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("멋쟁이 토마토", LocalDateTime.now().minusDays(17), StatType.창의력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("오늘부터 갓생 산다.", LocalDateTime.now().minusDays(10), StatType.창의력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("내가 최고라지요", LocalDateTime.now().minusDays(7), StatType.창의력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("나야나", LocalDateTime.now().minusDays(1), StatType.창의력, 10),
				member.getId()));

		// when
		statTitleEventHandler.checkIfGetTitleOrNot(member, StatType.창의력);

		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(3);
	}

	@DisplayName("최애력의 stat 포인트가 90이라면, 최애력 관련 모든 칭호가 발급됩니다.")
	@Test
	void When_LoveStatMoreThan90_Expect_GetAllOfLoveStatTitle() {
		// given
		Title title1 = createTitle(16L, "최애C+", "최C+");
		Title title2 = createTitle(17L, "최애B+", "초ㅣB+");
		Title title3 = createTitle(18L, "최애A+", "최ㅏA+");
		titleRepository.saveAll(List.of(title1, title2, title3));

		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("늘해랑 가입 환영", LocalDateTime.now().minusDays(20), StatType.최애력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("멋쟁이 토마토", LocalDateTime.now().minusDays(17), StatType.최애력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("오늘부터 갓생 산다.", LocalDateTime.now().minusDays(10), StatType.최애력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("내가 최고라지요", LocalDateTime.now().minusDays(7), StatType.최애력, 20),
				member.getId()));
		memberStatRepository.save(
			StatRecord.of(StatRecordReqDto.of("나야나", LocalDateTime.now().minusDays(1), StatType.최애력, 10),
				member.getId()));

		// when
		statTitleEventHandler.checkIfGetTitleOrNot(member, StatType.최애력);

		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(3);
	}

	private static Title createTitle(Long id, String name, String content) {
		return Title.builder()
			.id(id)
			.name(name)
			.content(content)
			.build();
	}
}