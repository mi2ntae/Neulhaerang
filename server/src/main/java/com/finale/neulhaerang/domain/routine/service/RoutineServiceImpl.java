package com.finale.neulhaerang.domain.routine.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineModifyReqDto;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineRemoveReqDto;
import com.finale.neulhaerang.domain.routine.dto.response.DailyRoutineResDto;
import com.finale.neulhaerang.domain.routine.dto.response.RoutineResDto;
import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.repository.DailyRoutineRepository;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;
import com.finale.neulhaerang.global.exception.routine.AlreadyRemoveDailyRoutineException;
import com.finale.neulhaerang.global.exception.routine.AlreadyRemoveRoutineException;
import com.finale.neulhaerang.global.exception.routine.CanNotRemoveBeforeTodayException;
import com.finale.neulhaerang.global.exception.routine.InvalidRepeatedDateException;
import com.finale.neulhaerang.global.exception.routine.NotExistAlarmTimeException;
import com.finale.neulhaerang.global.exception.routine.NotExistDailyRoutineException;
import com.finale.neulhaerang.global.exception.routine.NotExistRelationWithRoutineException;
import com.finale.neulhaerang.global.exception.routine.NotExistRoutineException;
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
		StringBuilder repeated = checkRepeatedDate(routineCreateReqDto.getRepeated());
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
				.map(r -> RoutineResDto.of(r, changeRepeated(r.getRepeated())))
				.collect(Collectors.toList());
		} else {
			Member member = memberRepository.getReferenceById(authenticationHandler.getLoginMemberId());
			List<DailyRoutine> dailyRoutines = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutine_MemberAndStatusIsFalse(
				date, member);
			return dailyRoutines.stream()
				.map(r -> DailyRoutineResDto.of(r, changeRepeated(r.getRoutine().getRepeated())))
				.collect(Collectors.toList());
		}
	}

	@Override
	public void modifyDailyRoutineCheckByDailyRoutineId(Long dailyRoutineId) {
		Optional<DailyRoutine> optionalDailyRoutine = dailyRoutineRepository.findById(dailyRoutineId);
		Optional<Member> optionalMember = memberRepository.findById(authenticationHandler.getLoginMemberId());
		if (optionalDailyRoutine.isEmpty()) {
			throw new NotExistDailyRoutineException(
				optionalMember.get(), dailyRoutineId);
		}

		if (optionalDailyRoutine.get().isStatus()) {
			throw new AlreadyRemoveDailyRoutineException(
				optionalMember.get(),
				optionalDailyRoutine.get());
		}
		optionalDailyRoutine.get().updateCheck();
	}

	@Override
	public void modifyRoutineContentAndRepeatedAndAlarmAndAlarmTimeByRoutineId(Long routineId,
		RoutineModifyReqDto routineModifyReqDto) {
		Optional<Member> member = memberRepository.findById(authenticationHandler.getLoginMemberId());
		Optional<Routine> optionalRoutine = routineRepository.findById(routineId);
		if (optionalRoutine.isEmpty()) {
			throw new NotExistRoutineException(member.get(), routineId);
		}
		if (optionalRoutine.get().getDeleteDate() != null && !optionalRoutine.get()
			.getDeleteDate()
			.isAfter(LocalDate.now())) {
			throw new AlreadyRemoveRoutineException(member.get(), optionalRoutine.get());
		}
		if (routineModifyReqDto.isAlarm()) {
			if (routineModifyReqDto.getAlarmTime() == null) {
				throw new NotExistAlarmTimeException(member.get());
			}
		}
		if (routineModifyReqDto.getRepeated().size() != 7) {
			throw new InvalidRepeatedDateException(member.get());
		}
		StringBuilder repeated = checkRepeatedDate(routineModifyReqDto.getRepeated());
		optionalRoutine.get()
			.updateContentAndAlarmAndAlarmTimeAndRepeated(routineModifyReqDto, repeated.toString());
	}

	@Override
	public void removeRoutineByRoutineId(RoutineRemoveReqDto routineRemoveReqDto) {
		Optional<DailyRoutine> optionalDailyRoutine = dailyRoutineRepository.findById(
			routineRemoveReqDto.getDailyRoutineId());
		if (optionalDailyRoutine.isEmpty()) {
			throw new NotExistDailyRoutineException(
				memberRepository.findById(authenticationHandler.getLoginMemberId()).get(),
				routineRemoveReqDto.getDailyRoutineId());
		}
		if (optionalDailyRoutine.get().isStatus()) {
			throw new AlreadyRemoveDailyRoutineException(
				memberRepository.findById(authenticationHandler.getLoginMemberId()).get(),
				optionalDailyRoutine.get());
		}
		if (optionalDailyRoutine.get().getRoutineDate().isBefore(LocalDate.now())) {
			throw new CanNotRemoveBeforeTodayException(
				memberRepository.findById(authenticationHandler.getLoginMemberId()).get(),
				optionalDailyRoutine.get());
		}
		optionalDailyRoutine.get().updateStatus();
		if (!routineRemoveReqDto.isNever()) {
			return;
		}
		Optional<Routine> optionalRoutine = routineRepository.findById(routineRemoveReqDto.getRoutineId());
		if (optionalRoutine.isEmpty()) {
			throw new NotExistRoutineException(
				memberRepository.findById(authenticationHandler.getLoginMemberId()).get(),
				routineRemoveReqDto.getRoutineId());
		}
		if (!optionalRoutine.get().equals(optionalDailyRoutine.get().getRoutine())) {
			throw new NotExistRelationWithRoutineException(
				memberRepository.findById(authenticationHandler.getLoginMemberId()).get(), optionalDailyRoutine.get(),
				optionalRoutine.get());
		}
		if (optionalRoutine.get().getDeleteDate() != null) {
			throw new AlreadyRemoveRoutineException(
				memberRepository.findById(authenticationHandler.getLoginMemberId()).get(),
				optionalRoutine.get());
		}
		optionalRoutine.get().updateDeleteDate(LocalDate.now());
	}

	private StringBuilder checkRepeatedDate(List<Boolean> repeat) {
		StringBuilder repeated = new StringBuilder();
		repeat.forEach(r ->
			repeated.append(r ? "1" : "0")
		);
		return repeated;
	}

	private List<Boolean> changeRepeated(String repeated) {
		List<Boolean> date = new ArrayList<>();
		for (char r : repeated.toCharArray()) {
			date.add(r == '1');
		}
		return date;
	}
}
