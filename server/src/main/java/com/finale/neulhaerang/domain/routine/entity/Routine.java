package com.finale.neulhaerang.domain.routine.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import com.finale.neulhaerang.global.util.BaseTimeEntity;

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

	@Column(length = 100, nullable = false)
	private String content;

	@Column(length = 7, nullable = false)
	private String repeated;

	@ColumnDefault("false")
	@Column(columnDefinition = "TINYINT(1)", nullable = false)
	private boolean alarm;

	private LocalDateTime alarmTime;

	private LocalDateTime deleteDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatType statType;
}
