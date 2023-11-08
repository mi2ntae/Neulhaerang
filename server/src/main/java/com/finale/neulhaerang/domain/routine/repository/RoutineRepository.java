package com.finale.neulhaerang.domain.routine.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.finale.neulhaerang.domain.routine.entity.Routine;

public interface RoutineRepository extends JpaRepository<Routine, Long> {

	@Query(value = "select * "
		+ "from routine r "
		+ "where r.repeated like :dayOfValue "
		+ "and (r.delete_date > :date or r.delete_date is null)", nativeQuery = true)
	List<Routine> findRoutinesByDayOfValue(String dayOfValue, LocalDate date);

	@Query(value = "select * "
		+ "from routine r "
		+ "where r.repeated like :dayOfValue "
		+ "and (r.delete_date > :date or r.delete_date is null) "
		+ "and r.alarm=true "
		+ "and r.alarm_time is not null", nativeQuery = true)
	List<Routine> findRoutinesByDayOfValueAndAlarmIsTrueAndAlarmTimeIsNotNull(String dayOfValue, LocalDate date);

}
