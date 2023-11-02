package com.finale.neulhaerang.domain.routine.service;

import static org.assertj.core.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;
import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.routine.repository.DailyRoutineRepository;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;
import com.finale.neulhaerang.global.exception.common.InvalidRepeatedDateException;
import com.finale.neulhaerang.global.exception.common.NotExistAlarmTimeException;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@ActiveProfiles("test")
class RoutineServiceTest {

	@Autowired
	private RoutineService routineService;

	@Autowired
	private RoutineRepository routineRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DailyRoutineRepository dailyRoutineRepository;

	private Member save;

	@BeforeAll
	public void createTestMember() {
		Member member = Member.builder()
			.nickname("박정은")
			.kakaoId(12345678L).build();
		save = memberRepository.save(member);
	}

	@DisplayName("알람이 존재하는 루틴을 생성합니다.")
	@Test
	void When_CreateRoutineWithAlarm_Expect_isCreated() {

		// given
		RoutineCreateReqDto routineCreateReqDto = createRoutine("아침밥 챙겨랏 S2", true, LocalTime.of(8, 30, 0),
			List.of(true, true, true, false, false, false, false), StatType.생존력);
		// when
		routineService.createRoutine(routineCreateReqDto);

		// then
		List<Routine> routines = routineRepository.findAll();
		assertThat(routines).hasSize(1)
			.extracting("content", "repeated", "alarm", "alarmTime", "deleteDate", "statType")
			.containsExactlyInAnyOrder(
				tuple("아침밥 챙겨랏 S2", "1110000", true, LocalTime.of(8, 30, 0), null, StatType.생존력)
			);
	}

	@DisplayName("알람이 없는 루틴을 생성합니다.")
	@Test
	void When_CreateRoutineWithoutAlarm_Expect_isCreated() {

		// given
		RoutineCreateReqDto routineCreateReqDto = createRoutine("아침밥 챙겨랏 S2", false, LocalTime.of(8, 30, 0),
			List.of(true, true, true, false, false, false, false), StatType.생존력);
		// when
		routineService.createRoutine(routineCreateReqDto);

		// then
		List<Routine> routines = routineRepository.findAll();
		assertThat(routines).hasSize(1)
			.extracting("content", "repeated", "alarm", "alarmTime", "deleteDate", "statType")
			.containsExactlyInAnyOrder(
				tuple("아침밥 챙겨랏 S2", "1110000", false, null, null, StatType.생존력)
			);
	}

	@DisplayName("알람이 존재하는 루틴을 생성 시, 알람 시간이 없으면 에러가 납니다.")
	@Test
	void When_CreateRoutineWithoutAlarmTime_Expect_isBadRequest() {

		// given
		RoutineCreateReqDto routineCreateReqDto = createRoutine("아침밥 챙겨랏 S2", true, null,
			List.of(true, true, true, false, false, false, false), StatType.생존력);
		// when // then
		assertThatThrownBy(() -> routineService.createRoutine(routineCreateReqDto))
			.isInstanceOf(NotExistAlarmTimeException.class);
	}

	@DisplayName("루틴을 생성 시, 반복 날짜 정보를 담은 리스트의 크기가 7이 아니면 에러가 납니다.")
	@Test
	void When_CreateRoutineWithoutInvalidSizeRepeatedList_Expect_isBadRequest() {

		// given
		RoutineCreateReqDto routineCreateReqDto = createRoutine("아침밥 챙겨랏 S2", true, LocalTime.of(8, 30, 0),
			List.of(true, true, true, false, false, false), StatType.생존력);
		// when // then
		assertThatThrownBy(() -> routineService.createRoutine(routineCreateReqDto))
			.isInstanceOf(InvalidRepeatedDateException.class);
	}

	@DisplayName("오늘 날짜 이전의 루틴을 조회하는 경우 daily_routine을 조회합니다.")
	@Test
	void When_findRoutineWithBeforeToday_Expect_findDailyRoutine() {
		// given
		LocalDate date = LocalDate.now();
		Routine routine1 = createRoutine(save, "양치하기1", "0010000", false, StatType.생존력);
		Routine routine2 = createRoutine(save, "양치하기2", "0110000", false, StatType.생존력);
		Routine routine3 = createRoutine(save, "양치하기3", "0111000", false, StatType.생존력);
		routineRepository.saveAll(List.of(routine1, routine2, routine3));

		DailyRoutine dailyRoutine1 = createDailyRoutine(routine1, true, date);
		DailyRoutine dailyRoutine2 = createDailyRoutine(routine2, false, date.minusDays(1));
		DailyRoutine dailyRoutine3 = createDailyRoutine(routine3, false, date);
		dailyRoutineRepository.saveAll(List.of(dailyRoutine1, dailyRoutine2, dailyRoutine3));

		// when
		List<?> dailyRoutines = routineService.findRoutineByMemberAndDate(date);

		// then
		assertThat(dailyRoutines).hasSize(2)
			.extracting("content", "check", "date")
			.containsExactlyInAnyOrder(
				tuple("양치하기1", true, date),
				tuple("양치하기3", false, date)
			);
	}

	@DisplayName("오늘 날짜 이후 루틴을 조회하는 경우 routine을 조회합니다.")
	@Test
	void When_findRoutineWithAfterToday_Expect_findRoutine() {
		// given
		LocalDate currentDate = LocalDate.now();

		// 현재 요일 가져오기
		DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();

		// 다음 주의 수요일로 이동
		int daysUntilNextWednesday = DayOfWeek.WEDNESDAY.getValue() - currentDayOfWeek.getValue();
		if (daysUntilNextWednesday <= 0) {
			daysUntilNextWednesday += 7;
		}
		LocalDate date = currentDate.plusDays(daysUntilNextWednesday);

		Routine routine1 = createRoutine(save, "양치하기1", "0010000", false, StatType.생존력);
		Routine routine2 = createRoutine(save, "양치하기2", "0110000", false, StatType.생존력);
		Routine routine3 = createRoutine(save, "양치하기3", "0101000", false, StatType.생존력);
		routineRepository.saveAll(List.of(routine1, routine2, routine3));

		// when
		List<?> routines = routineService.findRoutineByMemberAndDate(date);

		// then
		assertThat(routines).hasSize(2)
			.extracting("content", "date")
			.containsExactlyInAnyOrder(
				tuple("양치하기1", date),
				tuple("양치하기2", date)
			);
	}

	private static RoutineCreateReqDto createRoutine(String content, boolean alarm, LocalTime alarmTime,
		List<Boolean> repeated, StatType statType) {
		return RoutineCreateReqDto.builder()
			.content(content)
			.alarm(alarm)
			.alarmTime(alarmTime)
			.repeated(repeated)
			.statType(statType)
			.build();
	}

	private static Routine createRoutine(Member save, String content, String repeated, boolean alarm,
		StatType statType) {
		return Routine.builder()
			.member(save)
			.content(content)
			.repeated(repeated)
			.alarm(alarm)
			.statType(statType)
			.build();
	}

	private static DailyRoutine createDailyRoutine(Routine routine, boolean check, LocalDate date) {
		return DailyRoutine.builder()
			.routine(routine)
			.check(check)
			.routineDate(date)
			.build();
	}
}
