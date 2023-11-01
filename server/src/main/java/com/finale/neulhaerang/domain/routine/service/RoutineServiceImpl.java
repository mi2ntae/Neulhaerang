package com.finale.neulhaerang.domain.routine.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;
import com.finale.neulhaerang.global.exception.common.InvalidRepeatedDateException;
import com.finale.neulhaerang.global.exception.common.NotExistAlarmTimeException;
import com.finale.neulhaerang.global.util.AuthenticationHandler;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {
	private final RoutineRepository routineRepository;
	private final AuthenticationHandler authenticationHandler;
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
		return null;
	}

	private static StringBuilder checkRepeatedDate(RoutineCreateReqDto routineCreateReqDto) {
		StringBuilder repeated = new StringBuilder();
		routineCreateReqDto.getRepeated().forEach(r ->
			repeated.append(r ? "1" : "0")
		);
		return repeated;
	}
}
