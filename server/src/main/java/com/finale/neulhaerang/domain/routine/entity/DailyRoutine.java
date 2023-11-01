package com.finale.neulhaerang.domain.routine.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "daily_routine")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyRoutine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "daily_routine_id")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "routine_id", nullable = false)
	private Routine routine;

	@Column(name = "is_check", nullable = false)
	private boolean check;

	@Column(nullable = false)
	private LocalDate routineDate;

	public static DailyRoutine create(Routine routine, LocalDate date) {
		return DailyRoutine.builder()
			.routine(routine)
			.check(false)
			.routineDate(date)
			.build();
	}
}
