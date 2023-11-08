package com.finale.neulhaerang.global.util;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.finale.neulhaerang.domain.letter.entity.Letter;
import com.finale.neulhaerang.domain.letter.repository.LetterRepository;
import com.finale.neulhaerang.domain.member.document.StatRecord;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberStatRepository;
import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.routine.repository.DailyRoutineRepository;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;
import com.finale.neulhaerang.domain.todo.entity.Todo;
import com.finale.neulhaerang.domain.todo.repository.TodoRepository;

// @AutoConfigureMockMvc
@WithMockUser(password = "1")
class SchedulerTest extends BaseTest {

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private RoutineRepository routineRepository;

	@Autowired
	private DailyRoutineRepository dailyRoutineRepository;

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private MemberStatRepository memberStatRepository;

	@Autowired
	private LetterRepository letterRepository;

	@DisplayName("스케줄러가 실행되면 해당 날짜의 daily-routine이 추가됩니다.")
	@Test
	void When_Scheduler_Expect_AddDailyRoutine() {
		// given
		Routine routine1 = createRoutine("양치하기", "0010000", false, StatType.생존력);
		Routine routine2 = createRoutine("양치하기2", "0110000", false, StatType.생존력);
		Routine routine3 = createRoutine("양치하기3", "0101000", false, StatType.생존력);

		List<Routine> routines = List.of(routine1, routine2, routine3);
		routineRepository.saveAll(routines);
		LocalDate date = LocalDate.of(2023, 11, 1);

		// when
		scheduler.createDailyRoutine(date);

		// then
		List<DailyRoutine> dailyRoutines = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutineIn(
			date, routines);
		assertThat(dailyRoutines).hasSize(2)
			.extracting("routine", "check", "routineDate")
			.containsExactlyInAnyOrder(
				tuple(routine1, false, date),
				tuple(routine2, false, date)
			);
	}

	@Test
	@DisplayName("스케줄러가 실행되면 전날 완료한 투두, 루틴에 관련한 스탯이 증가합니다.")
	void When_Scheduler_Expect_ModifyMemberStat() {
		// given
		LocalDate date = LocalDate.now().minusDays(1);
		createTodoAndRoutine(date);

		List<Member> memberList = new ArrayList<>();
		memberList.add(member);

		// when
		scheduler.modifyStat(memberList, date);

		// then
		List<StatRecord> records = memberStatRepository.findAll();
		int[] stats = new int[StatType.values().length];
		records.forEach(record -> stats[record.getStatType().ordinal()] += record.getWeight());

		assertThat(stats[0]).isEqualTo(2); // 갓생력
		assertThat(stats[1]).isEqualTo(7); // 생존력
		assertThat(stats[2]).isEqualTo(2); // 인싸력
		assertThat(stats[3]).isEqualTo(10); // 튼튼력
		assertThat(stats[4]).isEqualTo(2); // 창의력
		assertThat(stats[5]).isEqualTo(12); // 최애력
		assertThat(stats[6]).isEqualTo(0); // 나태도
		assertThat(stats[7]).isEqualTo(10); // 피곤도
	}

	@Test
	@DisplayName("스케줄러가 실행되면 전 날 완료한 투두와 루틴 리스트로 편지를 작성해 줍니다.")
	void When_Scheduler_Expect_GenerateLetterByTodoAndRoutine() {
		// given
		LocalDate date = LocalDate.now().minusDays(1);
		createTodoAndRoutine(date);

		List<Member> memberList = new ArrayList<>();
		memberList.add(member);

		// when
		scheduler.createLetter(memberList, date);

		// then
		List<Letter> letter = letterRepository.findAll();
		assertThat(letter).hasSize(1);
	}

	private void createTodoAndRoutine(LocalDate date) {
		Todo todo1 = this.createTodo("7시 미라클 모닝", StatType.생존력, date.atTime(13, 30), true);
		Todo todo2 = this.createTodo("친구랑 곱창 먹기", StatType.인싸력, date.atTime(13, 30), true);
		Todo todo3 = this.createTodo("헬스장 가기", StatType.갓생력, date.atTime(13, 30), true);
		Todo todo4 = this.createTodo("코딩 문제 풀기", StatType.창의력, date.atTime(13, 30), true);
		Todo todo5 = this.createTodo("단지 이뻐해주기", StatType.최애력, date.atTime(13, 30), true);
		Todo todo6 = this.createTodo("연예인 사진 찾아보기", StatType.최애력, date.atTime(13, 30), false);
		todoRepository.saveAll(List.of(todo1, todo2, todo3, todo4, todo5, todo6));

		Routine routine1 = createRoutine("애견카페가기", "0010000", false, StatType.최애력);
		Routine routine2 = createRoutine("산책가기", "0110000", false, StatType.튼튼력);
		Routine routine3 = createRoutine("CS 스터디", "0101000", false, StatType.생존력);
		routineRepository.saveAll(List.of(routine1, routine2, routine3));

		DailyRoutine dailyRoutine1 = createDailyRoutine(routine1, true, date);
		DailyRoutine dailyRoutine2 = createDailyRoutine(routine1, true, date);
		DailyRoutine dailyRoutine3 = createDailyRoutine(routine2, true, date);
		DailyRoutine dailyRoutine4 = createDailyRoutine(routine2, true, date);
		DailyRoutine dailyRoutine5 = createDailyRoutine(routine3, true, date);
		DailyRoutine dailyRoutine6 = createDailyRoutine(routine3, false, date);
		dailyRoutineRepository.saveAll(
			List.of(dailyRoutine1, dailyRoutine2, dailyRoutine3, dailyRoutine4, dailyRoutine5, dailyRoutine6));
	}

	private Routine createRoutine(String content, String repeated, boolean alarm, StatType statType) {
		return Routine.builder()
			.member(member)
			.content(content)
			.repeated(repeated)
			.alarm(alarm)
			.statType(statType)
			.build();
	}

	private Todo createTodo(String content, StatType statType, LocalDateTime todoDate, boolean check) {
		return Todo.builder()
			.member(member)
			.todoDate(todoDate)
			.content(content)
			.statType(statType)
			.check(check)
			.build();
	}

	private DailyRoutine createDailyRoutine(Routine routine, boolean check, LocalDate date) {
		return DailyRoutine.builder()
			.routine(routine)
			.check(check)
			.routineDate(date)
			.status(false)
			.build();
	}
}