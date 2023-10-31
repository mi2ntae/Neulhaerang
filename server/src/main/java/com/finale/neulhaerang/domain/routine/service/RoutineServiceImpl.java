package com.finale.neulhaerang.domain.routine.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;
import com.finale.neulhaerang.global.exception.common.InvalidRepeatedDateException;
import com.finale.neulhaerang.global.exception.common.NotExistAlarmTimeException;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {
	private final RoutineRepository routineRepository;

	@Override
	public void createRoutine(Member member, RoutineCreateReqDto routineCreateReqDto) {
		if (routineCreateReqDto.isAlarm()) {
			if (routineCreateReqDto.getAlarmTime() == null) {
				throw new NotExistAlarmTimeException(member);
			}
		}
		if (routineCreateReqDto.getRepeated().size() != 7) {
			throw new InvalidRepeatedDateException(member);
		}
		StringBuilder repeated = checkRepeatedDate(routineCreateReqDto);
		Routine routine = Routine.create(routineCreateReqDto, member, repeated.toString());
		routineRepository.save(routine);
	}

	private static StringBuilder checkRepeatedDate(RoutineCreateReqDto routineCreateReqDto) {
		StringBuilder repeated = new StringBuilder();
		routineCreateReqDto.getRepeated().forEach(r -> {
			if (r) {
				repeated.append("1");
			} else {
				repeated.append("0");
			}
		});
		return repeated;
	}
}
