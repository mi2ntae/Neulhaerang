package com.finale.neulhaerang.domain.routine.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineCreateReqDto;
import com.finale.neulhaerang.domain.routine.dto.request.RoutineModifyReqDto;
import com.finale.neulhaerang.global.exception.routine.InvalidRepeatedDateException;
import com.finale.neulhaerang.global.exception.routine.NonRepeatedDateException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Routine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "routine_id")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(length = 100, nullable = false)
	private String content;

	@Column(length = 7, nullable = false)
	private String repeated;

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean alarm;

	private LocalTime alarmTime;

	private LocalDate deleteDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatType statType;

	public static Routine create(RoutineCreateReqDto routineCreateReqDto, Member member, String repeated) {
		if (repeated.equals("0000000")) {
			throw new NonRepeatedDateException();
		}
		if (repeated.length() != 7) {
			throw new InvalidRepeatedDateException();
		}
		return Routine.builder()
			.member(member)
			.repeated(repeated)
			.content(routineCreateReqDto.getContent())
			.alarm(routineCreateReqDto.isAlarm())
			.alarmTime(routineCreateReqDto.isAlarm() ? routineCreateReqDto.getAlarmTime() : null)
			.statType(routineCreateReqDto.getStatType())
			.build();
	}

	public void updateContentAndAlarmAndAlarmTimeAndRepeated(RoutineModifyReqDto routineModifyReqDto, String repeated) {
		if (repeated.equals("0000000")) {
			throw new NonRepeatedDateException();
		}
		if (repeated.length() != 7) {
			throw new InvalidRepeatedDateException();
		}
		this.repeated = repeated;
		this.content = routineModifyReqDto.getContent();
		this.alarm = routineModifyReqDto.isAlarm();
		this.alarmTime = routineModifyReqDto.isAlarm() ? routineModifyReqDto.getAlarmTime() : null;
	}

	public void updateDeleteDate(LocalDate deleteDate) {
		this.deleteDate = deleteDate;
	}
}
