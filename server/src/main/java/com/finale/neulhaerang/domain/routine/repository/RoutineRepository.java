package com.finale.neulhaerang.domain.routine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finale.neulhaerang.domain.routine.entity.Routine;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
}
