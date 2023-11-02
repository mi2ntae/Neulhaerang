package com.finale.neulhaerang.domain.routine.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;
import com.finale.neulhaerang.domain.routine.entity.Routine;

public interface DailyRoutineRepository extends JpaRepository<DailyRoutine, Long> {
	List<DailyRoutine> findDailyRoutinesByRoutineDateAndRoutineIn(LocalDate routineDate, List<Routine> routines);

	List<DailyRoutine> findDailyRoutinesByRoutineDateAndRoutine_Member(LocalDate routineDate, Member member);
}
