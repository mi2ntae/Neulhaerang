package com.finale.neulhaerang.domain.member.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatChangeRecordResDto {
	private List<Integer> weights;

	public static StatChangeRecordResDto from (int[] weights) {
		List<Integer> weightList = new ArrayList<>();
		for(int w : weights) {
			weightList.add(w);
		}
		return StatChangeRecordResDto.builder()
			.weights(weightList).build();
	}
}
