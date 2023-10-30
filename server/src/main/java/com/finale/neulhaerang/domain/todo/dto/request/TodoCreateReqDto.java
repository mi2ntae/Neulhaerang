package com.finale.neulhaerang.domain.todo.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.finale.neulhaerang.domain.routine.entity.StatType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoCreateReqDto {
	@NotNull
	private LocalDateTime todoDate;

	@NotBlank
	private String content;

	@NotNull
	private StatType statType;
}
