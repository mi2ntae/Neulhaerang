package com.finale.neulhaerang.domain.todo.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CheckRatioListResDto {
	private LocalDate date;
	private double ratio;

	public static CheckRatioListResDto of(LocalDate date, double ratio){
		return CheckRatioListResDto.builder()
			.date(date)
			.ratio(ratio)
			.build();
	}
}
