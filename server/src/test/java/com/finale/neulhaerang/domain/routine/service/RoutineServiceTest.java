package com.finale.neulhaerang.domain.routine.service;

import static org.assertj.core.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineModifyReqDto;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineRemoveReqDto;
import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.routine.repository.DailyRoutineRepository;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;
import com.finale.neulhaerang.global.exception.routine.AlreadyRemoveDailyRoutineException;
import com.finale.neulhaerang.global.exception.routine.AlreadyRemoveRoutineException;
import com.finale.neulhaerang.global.exception.routine.InvalidRepeatedDateException;
import com.finale.neulhaerang.global.exception.routine.NotExistAlarmTimeException;
import com.finale.neulhaerang.global.exception.routine.NotExistDailyRoutineException;
import com.finale.neulhaerang.global.exception.routine.NotExistRoutineException;
import com.finale.neulhaerang.global.util.BaseTest;

class RoutineServiceTest extends BaseTest {

	@Autowired
	private RoutineService routineService;

	@Autowired
	private RoutineRepository routineRepository;

	@Autowired
	private DailyRoutineRepository dailyRoutineRepository;

	@DisplayName("알람이 존재하는 루틴을 생성합니다.")
	@Test
	void When_CreateRoutineWithAlarm_Expect_IsCreated() {
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
	void When_CreateRoutineWithoutAlarm_Expect_IsCreated() {
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
	void When_CreateRoutineWithoutAlarmTime_Expect_NotExistAlarmTimeException() {
		// given
		RoutineCreateReqDto routineCreateReqDto = createRoutine("아침밥 챙겨랏 S2", true, null,
			List.of(true, true, true, false, false, false, false), StatType.생존력);
		// when // then
		assertThatThrownBy(() -> routineService.createRoutine(routineCreateReqDto))
			.isInstanceOf(NotExistAlarmTimeException.class);
	}

	@DisplayName("루틴을 생성 시, 반복 날짜 정보를 담은 리스트의 크기가 7이 아니면 에러가 납니다.")
	@Test
	void When_CreateRoutineWithoutInvalidSizeRepeatedList_Expect_InvalidRepeatedDateException() {
		// given
		RoutineCreateReqDto routineCreateReqDto = createRoutine("아침밥 챙겨랏 S2", true, LocalTime.of(8, 30, 0),
			List.of(true, true, true, false, false, false), StatType.생존력);
		// when // then
		assertThatThrownBy(() -> routineService.createRoutine(routineCreateReqDto))
			.isInstanceOf(InvalidRepeatedDateException.class);
	}

	@DisplayName("오늘 날짜 이전의 루틴을 조회하는 경우 daily_routine을 조회합니다.")
	@Test
	void When_FindRoutineWithBeforeToday_Expect_FindDailyRoutine() {
		// given
		LocalDate date = LocalDate.now();
		Routine routine1 = createRoutine(member, "양치하기1", "0010000", false, StatType.생존력);
		Routine routine2 = createRoutine(member, "양치하기2", "0110000", false, StatType.생존력);
		Routine routine3 = createRoutine(member, "양치하기3", "0111000", false, StatType.생존력);
		routineRepository.saveAll(List.of(routine1, routine2, routine3));

		DailyRoutine dailyRoutine1 = createDailyRoutine(routine1, true, date);
		DailyRoutine dailyRoutine2 = createDailyRoutine(routine2, false, date.minusDays(1));
		DailyRoutine dailyRoutine3 = createDailyRoutine(routine3, false, date);
		dailyRoutineRepository.saveAll(List.of(dailyRoutine1, dailyRoutine2, dailyRoutine3));

		// when
		List<?> dailyRoutines = routineService.findRoutineByMemberAndDate(date);

		// then
		assertThat(dailyRoutines).hasSize(2)
			.extracting("content", "check")
			.containsExactlyInAnyOrder(
				tuple("양치하기1", true),
				tuple("양치하기3", false)
			);
	}

	@DisplayName("오늘 날짜 이후 루틴을 조회하는 경우 routine을 조회합니다.")
	@Test
	void When_FindRoutineWithAfterToday_Expect_FindRoutine() {
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

		Routine routine1 = createRoutine(member, "양치하기1", "0010000", false, StatType.생존력);
		Routine routine2 = createRoutine(member, "양치하기2", "0110000", false, StatType.생존력);
		Routine routine3 = createRoutine(member, "양치하기3", "0101000", false, StatType.생존력);
		routineRepository.saveAll(List.of(routine1, routine2, routine3));

		// when
		List<?> routines = routineService.findRoutineByMemberAndDate(date);

		// then
		assertThat(routines).hasSize(2)
			.extracting("content")
			.containsExactlyInAnyOrder(
				"양치하기1",
				"양치하기2"
			);
	}

	@DisplayName("daily routine의 check 값을 변경합니다.")
	@Test
	void When_ModifyDailyRoutineCheck_Expect_OppositeDailyRoutineCheck() {
		// given
		Routine routine = createRoutine(member, "양치하기1", "0010000", false, StatType.생존력);
		routineRepository.save(routine);
		boolean original = true;
		DailyRoutine dailyRoutine = createDailyRoutine(routine, original, false);
		DailyRoutine save = dailyRoutineRepository.save(dailyRoutine);

		// when
		routineService.modifyDailyRoutineCheckByDailyRoutineId(dailyRoutine.getId());

		// then
		Optional<DailyRoutine> optionalDailyRoutine = dailyRoutineRepository.findById(save.getId());
		assertThat(optionalDailyRoutine.get().isCheck()).isEqualTo(!original);
	}

	@DisplayName("daily routine의 check 값을 변경합니다. 이때 존재하지 않는 daily routine이라면 에러가 발생합니다.")
	@Test
	void When_ModifyDailyRoutineCheckWithNotExistId_Expect_NotExistDailyRoutineException() {
		// given // when // then
		assertThatThrownBy(() -> routineService.modifyDailyRoutineCheckByDailyRoutineId(1L))
			.isInstanceOf(NotExistDailyRoutineException.class);
	}

	@DisplayName("daily routine의 check 값을 변경합니다. 이때 이미 삭제된 daily routine이라면 에러가 발생합니다.")
	@Test
	void When_ModifyDailyRoutineCheckWithAlreadyRemoveRoutine_Expect_AlreadyRemoveDailyRoutineException() {
		// given
		Routine routine = createRoutine(member, "양치하기1", "0010000", false, StatType.생존력);
		routineRepository.save(routine);
		DailyRoutine dailyRoutine = createDailyRoutine(routine, true, true);
		DailyRoutine save = dailyRoutineRepository.save(dailyRoutine);
		// when // then
		assertThatThrownBy(() -> routineService.modifyDailyRoutineCheckByDailyRoutineId(save.getId()))
			.isInstanceOf(AlreadyRemoveDailyRoutineException.class);
	}

	@DisplayName("루틴 정보를 수정합니다. 내용과 알림 여부, 알림 시간, 반복 날짜를 수정할 수 있습니다.")
	@Test
	void When_ModifyRoutine_Expect_ModifyRoutine() {
		// given
		Routine routine1 = createRoutine(member, "양치하기", "0010000", false, StatType.생존력);
		Routine routine2 = createRoutine(member, "양치하기", "0010000", true, StatType.생존력, LocalTime.of(8, 30, 0));
		Routine routine3 = createRoutine(member, "양치하기", "0010000", false, StatType.생존력, LocalDate.now().plusDays(1));
		List<Routine> routines = routineRepository.saveAll(List.of(routine1, routine2, routine3));

		RoutineModifyReqDto routineModifyReqDto1 = createRoutineModifyDto(routines.get(0).getId(), true,
			LocalTime.of(8, 10, 0), "양치 꼭 하기", List.of(true, true, true, true, true, true, true));

		RoutineModifyReqDto routineModifyReqDto2 = createRoutineModifyDto(routines.get(1).getId(), false,
			null, "양치 꼭 하기", List.of(true, true, true, true, true, true, true));

		RoutineModifyReqDto routineModifyReqDto3 = createRoutineModifyDto(routines.get(2).getId(), true,
			LocalTime.of(8, 10, 0), "양치 꼭 하기", List.of(true, true, true, true, true, true, true));

		// when
		routineService.modifyRoutineContentAndRepeatedAndAlarmAndAlarmTimeByRoutineId(routineModifyReqDto1);
		routineService.modifyRoutineContentAndRepeatedAndAlarmAndAlarmTimeByRoutineId(routineModifyReqDto2);
		routineService.modifyRoutineContentAndRepeatedAndAlarmAndAlarmTimeByRoutineId(routineModifyReqDto3);

		// then
		List<Routine> routine = routineRepository.findAll();
		assertThat(routine).hasSize(3)
			.extracting("id", "repeated", "content", "alarm", "alarmTime")
			.containsExactlyInAnyOrder(
				tuple(routines.get(0).getId(), "1111111", "양치 꼭 하기", true, LocalTime.of(8, 10, 0)),
				tuple(routines.get(1).getId(), "1111111", "양치 꼭 하기", false, null),
				tuple(routines.get(2).getId(), "1111111", "양치 꼭 하기", true, LocalTime.of(8, 10, 0))
			);
	}

	@DisplayName("루틴 수정 시, 해당 id가 없으면 에러가 납니다.")
	@Test
	void When_ModifyRoutineWithInvalidId_Expect_NotExistRoutineException() {
		// given
		RoutineModifyReqDto routineModifyReqDto = createRoutineModifyDto(1L, true,
			LocalTime.of(8, 10, 0), "양치 꼭 하기", List.of(true, true, true, true, true, true, true));

		// when // then
		assertThatThrownBy(
			() -> routineService.modifyRoutineContentAndRepeatedAndAlarmAndAlarmTimeByRoutineId(routineModifyReqDto))
			.isInstanceOf(NotExistRoutineException.class);
	}

	@DisplayName("루틴 수정 시, 해당 루틴이 이미 삭제되었다면 에러가 납니다.")
	@Test
	void When_ModifyAlreadyRemovedRoutine_Expect_AlreadyRemoveRoutineException() {
		// given
		Routine routine = createRoutine(member, "양치하기", "0010000", false, StatType.생존력, LocalDate.now());
		Routine save = routineRepository.save(routine);

		RoutineModifyReqDto routineModifyReqDto = createRoutineModifyDto(save.getId(), true,
			LocalTime.of(8, 10, 0), "양치 꼭 하기", List.of(true, true, true, true, true, true, true));

		// when // then
		assertThatThrownBy(
			() -> routineService.modifyRoutineContentAndRepeatedAndAlarmAndAlarmTimeByRoutineId(routineModifyReqDto))
			.isInstanceOf(AlreadyRemoveRoutineException.class);
	}

	@DisplayName("알람이 존재하는 루틴을 수정 시, 알람 시간이 없으면 에러가 납니다.")
	@Test
	void When_ModifyRoutineWithoutAlarmTime_Expect_NotExistAlarmTimeException() {
		// given
		Routine routine = createRoutine(member, "양치하기", "0010000", false, StatType.생존력);
		Routine save = routineRepository.save(routine);
		RoutineModifyReqDto routineModifyReqDto = createRoutineModifyDto(save.getId(), true, null, "아침밥",
			List.of(true, true, true, false, false, false, false));

		// when // then
		assertThatThrownBy(
			() -> routineService.modifyRoutineContentAndRepeatedAndAlarmAndAlarmTimeByRoutineId(routineModifyReqDto))
			.isInstanceOf(NotExistAlarmTimeException.class);
	}

	@DisplayName("루틴을 수정 시, 반복 날짜 정보를 담은 리스트의 크기가 7이 아니면 에러가 납니다.")
	@Test
	void When_ModifyRoutineWithoutInvalidSizeRepeatedList_Expect_isBadRequest() {
		// given
		Routine routine = createRoutine(member, "양치하기", "0010000", true, StatType.생존력);
		Routine save = routineRepository.save(routine);
		RoutineModifyReqDto routineModifyReqDto = createRoutineModifyDto(save.getId(), false, null, "아침밥",
			List.of(true, true, true, false, false, false));

		// when // then
		assertThatThrownBy(
			() -> routineService.modifyRoutineContentAndRepeatedAndAlarmAndAlarmTimeByRoutineId(routineModifyReqDto))
			.isInstanceOf(InvalidRepeatedDateException.class);
	}

	@DisplayName("루틴을 삭제합니다.")
	@Test
	void When_RemoveRoutine_Expect_RemoveRoutineAndDailyRoutine() {
		// given
		Routine routine1 = createRoutine(member, "양치하기", "0010000", false, StatType.생존력);
		Routine routine2 = createRoutine(member, "양치하기", "0010000", false, StatType.생존력);
		List<Routine> routines = routineRepository.saveAll(List.of(routine1, routine2));

		DailyRoutine dailyRoutine1 = createDailyRoutine(routines.get(0), true, false);
		DailyRoutine dailyRoutine2 = createDailyRoutine(routines.get(1), true, false);
		List<DailyRoutine> dailyRoutines = dailyRoutineRepository.saveAll(List.of(dailyRoutine1, dailyRoutine2));

		RoutineRemoveReqDto routineRemoveReqDto1 = RoutineRemoveReqDto.builder()
			.dailyRoutineId(routines.get(0).getId())
			.routineId(dailyRoutines.get(0).getId())
			.never(true)
			.build();

		RoutineRemoveReqDto routineRemoveReqDto2 = RoutineRemoveReqDto.builder()
			.dailyRoutineId(routines.get(1).getId())
			.routineId(dailyRoutines.get(1).getId())
			.never(false)
			.build();

		// when
		routineService.removeRoutineByRoutineId(routineRemoveReqDto1);
		routineService.removeRoutineByRoutineId(routineRemoveReqDto2);

		// then
		List<DailyRoutine> allOfDailyRoutine = dailyRoutineRepository.findAll();
		List<Routine> allOfRoutine = routineRepository.findAll();
		assertThat(allOfDailyRoutine).hasSize(0);
		assertThat(allOfRoutine).hasSize(1)
			.extracting("id", "content", "repeated", "alarm")
			.containsExactlyInAnyOrder(
				tuple(routines.get(1).getId(), "양치하기", "0010000", false)
			);
	}

	@DisplayName("루틴 삭제 시, 해당 루틴이 존재하지 않으면 에러가 납니다.")
	@Test
	void When_RemoveRoutineWithNotExistRoutine_Expect_NotExistRoutineException() {
		// given
		Routine routine = createRoutine(member, "양치하기", "0010000", false, StatType.생존력);
		Routine saveRoutine = routineRepository.save(routine);

		DailyRoutine dailyRoutine = createDailyRoutine(saveRoutine, true, false);
		DailyRoutine saveDailyRoutine = dailyRoutineRepository.save(dailyRoutine);

		RoutineRemoveReqDto routineRemoveReqDto = RoutineRemoveReqDto.builder()
			.dailyRoutineId(saveDailyRoutine.getId())
			.routineId(0L)
			.never(true)
			.build();

		// when // then
		assertThatThrownBy(
			() -> routineService.removeRoutineByRoutineId(routineRemoveReqDto))
			.isInstanceOf(NotExistRoutineException.class);
	}

	@DisplayName("루틴 삭제 시, 해당 데일리 루틴이 존재하지 않으면 에러가 납니다.")
	@Test
	void When_RemoveRoutineWithNotExistDailyRoutine_Expect_NotExistDailyRoutineException() {
		// given
		Routine routine = createRoutine(member, "양치하기", "0010000", false, StatType.생존력);
		Routine saveRoutine = routineRepository.save(routine);

		RoutineRemoveReqDto routineRemoveReqDto = RoutineRemoveReqDto.builder()
			.dailyRoutineId(0L)
			.routineId(saveRoutine.getId())
			.never(true)
			.build();

		// when // then
		assertThatThrownBy(
			() -> routineService.removeRoutineByRoutineId(routineRemoveReqDto))
			.isInstanceOf(NotExistDailyRoutineException.class);
	}

	private RoutineModifyReqDto createRoutineModifyDto(Long routineId, boolean alarm, LocalTime alarmTime,
		String content, List<Boolean> repeated) {

		return RoutineModifyReqDto.builder()
			.routineId(routineId)
			.alarm(alarm)
			.alarmTime(alarmTime)
			.content(content)
			.repeated(repeated)
			.build();
	}

	private DailyRoutine createDailyRoutine(Routine routine, boolean original, boolean status) {
		return DailyRoutine.builder()
			.routine(routine)
			.check(original)
			.routineDate(LocalDate.now())
			.status(status)
			.build();
	}

	private RoutineCreateReqDto createRoutine(String content, boolean alarm, LocalTime alarmTime,
		List<Boolean> repeated, StatType statType) {
		return RoutineCreateReqDto.builder()
			.content(content)
			.alarm(alarm)
			.alarmTime(alarmTime)
			.repeated(repeated)
			.statType(statType)
			.build();
	}

	private Routine createRoutine(Member save, String content, String repeated, boolean alarm,
		StatType statType) {
		return Routine.builder()
			.member(save)
			.content(content)
			.repeated(repeated)
			.alarm(alarm)
			.statType(statType)
			.build();
	}

	private Routine createRoutine(Member save, String content, String repeated, boolean alarm,
		StatType statType, LocalTime alarmTime) {
		return Routine.builder()
			.member(save)
			.content(content)
			.repeated(repeated)
			.alarm(alarm)
			.alarmTime(alarmTime)
			.statType(statType)
			.build();
	}

	private Routine createRoutine(Member save, String content, String repeated, boolean alarm,
		StatType statType, LocalDate deleteDate) {
		return Routine.builder()
			.member(save)
			.content(content)
			.repeated(repeated)
			.alarm(alarm)
			.deleteDate(deleteDate)
			.statType(statType)
			.build();
	}

	private DailyRoutine createDailyRoutine(Routine routine, boolean check, LocalDate date) {
		return DailyRoutine.builder()
			.routine(routine)
			.check(check)
			.routineDate(date)
			.build();
	}
}
