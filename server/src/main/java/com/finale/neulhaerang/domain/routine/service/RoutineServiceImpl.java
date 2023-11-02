package com.finale.neulhaerang.domain.routine.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;
import com.finale.neulhaerang.domain.routine.dto.response.DailyRoutineResDto;
import com.finale.neulhaerang.domain.routine.dto.response.RoutineResDto;
import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.repository.DailyRoutineRepository;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;
import com.finale.neulhaerang.global.exception.routine.AlreadyRemoveDailyRoutineException;
import com.finale.neulhaerang.global.exception.routine.InvalidRepeatedDateException;
import com.finale.neulhaerang.global.exception.routine.NotExistAlarmTimeException;
import com.finale.neulhaerang.global.exception.routine.NotExistDailyRoutineException;
import com.finale.neulhaerang.global.util.AuthenticationHandler;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {
	private final RoutineRepository routineRepository;
	private final AuthenticationHandler authenticationHandler;
	private final DailyRoutineRepository dailyRoutineRepository;
	private final MemberRepository memberRepository;

	@Override
	public void createRoutine(RoutineCreateReqDto routineCreateReqDto) {
		Optional<Member> member = memberRepository.findById(authenticationHandler.getLoginMemberId());
		if (routineCreateReqDto.isAlarm()) {
			if (routineCreateReqDto.getAlarmTime() == null) {
				throw new NotExistAlarmTimeException(member.get());
			}
		}
		if (routineCreateReqDto.getRepeated().size() != 7) {
			throw new InvalidRepeatedDateException(member.get());
		}
		StringBuilder repeated = checkRepeatedDate(routineCreateReqDto);
		Routine routine = Routine.create(routineCreateReqDto, member.get(), repeated.toString());
		routineRepository.save(routine);
	}

	@Override
	public List<?> findRoutineByMemberAndDate(LocalDate date) {
		if (date.isAfter(LocalDate.now())) {
			StringBuilder dayOfVaule = new StringBuilder("_______");
			int dayOfWeekValue = date.getDayOfWeek().getValue() - 1;
			dayOfVaule.setCharAt(dayOfWeekValue, '1');
			List<Routine> routines = routineRepository.findRoutinesByDayOfValue(dayOfVaule.toString(),
				date);
			return routines.stream()
				.map(RoutineResDto::from)
				.collect(Collectors.toList());
		} else {
			Member member = memberRepository.getReferenceById(authenticationHandler.getLoginMemberId());
			List<DailyRoutine> dailyRoutines = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutine_MemberAndStatusIsFalse(
				date, member);
			return dailyRoutines.stream()
				.map(DailyRoutineResDto::from)
				.collect(Collectors.toList());
		}
	}

	@Override
	public void modifyDailyRoutineCheckByDailyRoutineId(Long dailyRoutineId) {
		Optional<DailyRoutine> optionalDailyRoutine = dailyRoutineRepository.findById(dailyRoutineId);
		if (optionalDailyRoutine.isEmpty()) {
			throw new NotExistDailyRoutineException(
				memberRepository.getReferenceById(authenticationHandler.getLoginMemberId()), dailyRoutineId);
		}

		if (optionalDailyRoutine.get().isStatus()) {
			throw new AlreadyRemoveDailyRoutineException(
				memberRepository.getReferenceById(authenticationHandler.getLoginMemberId()),
				optionalDailyRoutine.get());
		}
		optionalDailyRoutine.get().updateCheck();
	}

	private static StringBuilder checkRepeatedDate(RoutineCreateReqDto routineCreateReqDto) {
		StringBuilder repeated = new StringBuilder();
		routineCreateReqDto.getRepeated().forEach(r ->
			repeated.append(r ? "1" : "0")
		);
		return repeated;
	}
}
