package com.finale.neulhaerang.domain.routine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;

public interface DailyRoutineRepository extends JpaRepository<DailyRoutine, Long> {
}
