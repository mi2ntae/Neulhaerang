package com.finale.neulhaerang.domain.routine.service;

import java.time.LocalDate;
import java.util.List;

import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;
import com.finale.neulhaerang.domain.routine.dto.response.RoutineResDto;

public interface RoutineService {
	void createRoutine(RoutineCreateReqDto routineCreateReqDto);

	List<RoutineResDto> findRoutineByMemberAndDate(LocalDate date);
}
