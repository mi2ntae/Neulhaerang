package com.finale.neulhaerang.domain.routine.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;
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
		StringBuilder repeated = checkRepeatedDate(routineCreateReqDto);
		Routine routine = Routine.create(routineCreateReqDto, member, repeated.toString());
		routineRepository.save(routine);
	}

	private static StringBuilder checkRepeatedDate(RoutineCreateReqDto routineCreateReqDto) {
		StringBuilder repeated = new StringBuilder("0000000");
		for (String day : routineCreateReqDto.getRepeated()) {
			switch (day) {
				case "월":
					repeated.setCharAt(0, '1');
					break;
				case "화":
					repeated.setCharAt(1, '1');
					break;
				case "수":
					repeated.setCharAt(2, '1');
					break;
				case "목":
					repeated.setCharAt(3, '1');
					break;
				case "금":
					repeated.setCharAt(4, '1');
					break;
				case "토":
					repeated.setCharAt(5, '1');
					break;
				case "일":
					repeated.setCharAt(6, '1');
					break;
			}
		}
		return repeated;
	}
}
