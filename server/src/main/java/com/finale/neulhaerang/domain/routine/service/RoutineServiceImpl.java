package com.finale.neulhaerang.domain.routine.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;

@Transactional
@Service
public class RoutineServiceImpl implements RoutineService {
	@Override
	public void createRoutine(Long memberId, RoutineCreateReqDto routineCreateReqDto) {

	}
}
