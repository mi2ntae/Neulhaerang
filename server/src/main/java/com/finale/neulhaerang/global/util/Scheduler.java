package com.finale.neulhaerang.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.finale.neulhaerang.domain.member.document.StatRecord;
import com.finale.neulhaerang.domain.member.dto.request.StatRecordReqDto;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.member.repository.MemberStatRepository;
import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.routine.repository.DailyRoutineRepository;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;
import com.finale.neulhaerang.domain.todo.entity.Todo;
import com.finale.neulhaerang.domain.todo.repository.TodoRepository;
import com.finale.neulhaerang.global.exception.member.NotExistMemberException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Scheduler {

	private final RoutineRepository routineRepository;
	private final DailyRoutineRepository dailyRoutineRepository;
	private final TodoRepository todoRepository;
	private final MemberRepository memberRepository;
	private final AuthenticationHandler authenticationHandler;
	private final MemberStatRepository memberStatRepository;

	@Scheduled(cron = "${schedules.cron.daily-routine}", zone = "Asia/Seoul")
	public void createDailyRoutineTrigger() {
		createDailyRoutine(LocalDate.now());
		modifyStat(LocalDate.now().minusDays(1));
	}

	void createDailyRoutine(LocalDate date) {
		// date에 해야하는 루틴들을 가져옵니다.
		StringBuilder dayOfVaule = new StringBuilder("_______");
		int dayOfWeekValue = date.getDayOfWeek().getValue() - 1;
		dayOfVaule.setCharAt(dayOfWeekValue, '1');
		List<Routine> routinesOfDay = routineRepository.findRoutinesByDayOfValue(dayOfVaule.toString(), date);

		// dailyRoutine에 저장합니다.
		routinesOfDay.forEach(r -> {
			dailyRoutineRepository.save(DailyRoutine.create(r, date));
		});
	}

	void modifyStat(LocalDate date){
		long memberId = authenticationHandler.getLoginMemberId();
		Member member = memberRepository.findById(memberId)
			.orElseThrow(NotExistMemberException::new);

		// 그 날 완료한 투두 STAT 상승
		List<Todo> doneTodoList = todoRepository.findTodosByMemberAndStatusIsFalseAndCheckIsTrueAndTodoDateIsBetween(
			member, date.atStartOfDay(), date.atTime(LocalTime.MAX)
		);
		for(Todo todo : doneTodoList){
			createStatRecord(todo.getContent(), todo.getTodoDate(), todo.getStatType(), 2, memberId);
		}

		// 그 날 완료한 루틴 STAT 상승
		List<DailyRoutine> doneDailyRoutineList = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutine_MemberAndStatusIsFalseAndCheckIsTrue(date, member);
		for(DailyRoutine dailyRoutine : doneDailyRoutineList){
			createStatRecord(
				dailyRoutine.getRoutine().getContent(), dailyRoutine.getRoutineDate().atStartOfDay(), dailyRoutine.getRoutine().getStatType(), 5, memberId
			);
		}
		
		// 투두, 루틴 완료 갯수에 따라서 피곤도 STAT 증가
		int totalDone = doneTodoList.size() + doneDailyRoutineList.size();
		if(totalDone >= 30) {
			createStatRecord("완료한 일이 30개 이상", date.atTime(23,59), StatType.피곤도, 50, memberId);
		} else if(totalDone >= 20) {
			createStatRecord("완료한 일이 20개 이상", date.atTime(23,59), StatType.피곤도, 30, memberId);
		} else if(totalDone >= 10) {
			createStatRecord("완료한 일이 10개 이상", date.atTime(23,59), StatType.피곤도, 10, memberId);
		}

		// 투두, 루틴 완료 비율에 따라서 나태도 STAT 증가
		int totalTodo = todoRepository.findTodosByMemberAndStatusIsFalseAndTodoDateIsBetween(member, date.atStartOfDay(), date.atTime(LocalTime.MAX)).size();
		int totalRoutine = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutine_MemberAndStatusIsFalse(date, member).size();
		double ratio = (double)totalDone /(totalTodo+totalRoutine);
		if(ratio<0.2) {
			createStatRecord("80% 이상 완료하지 못했습니다.", date.atTime(23,59), StatType.나태도, 50, memberId);
		} else if(ratio<0.5){
			createStatRecord("50% 이상 완료하지 못했습니다.", date.atTime(23,59), StatType.나태도, 30, memberId);
		} else if(ratio<0.7){
			createStatRecord("30% 이상 완료하지 못했습니다.", date.atTime(23,59), StatType.나태도, 10, memberId);
		}
	}
	
	// 편지 생성

	void createStatRecord(String content, LocalDateTime dateTime, StatType statType, int weight, Long memberId){
		StatRecordReqDto statRecordReqDto = StatRecordReqDto.of(
			content, dateTime, statType, weight
		);
		memberStatRepository.save(StatRecord.of(statRecordReqDto, memberId));
	}
}
