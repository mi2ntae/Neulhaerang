package com.finale.neulhaerang.domain.member.document;

import java.time.LocalDateTime;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.finale.neulhaerang.domain.member.dto.request.StatRecordReqDto;
import com.finale.neulhaerang.domain.routine.entity.StatType;

import lombok.Builder;
import lombok.Getter;

@Document(collection = "member_stat")
@Getter
@Builder
public class StatRecord {
	@Id
	private ObjectId _id;
	private long memberId;
	private StatType statType;
	private LocalDateTime recordedDate;
	private int weight;
	private String reason;

	public static StatRecord of(StatRecordReqDto statRecordReqDto, long memberId) {
		return StatRecord.builder()
			.memberId(memberId)
			.statType(statRecordReqDto.getStatType())
			.recordedDate(statRecordReqDto.getRecordedDate().plusHours(9))
			.weight(statRecordReqDto.getWeight())
			.reason(statRecordReqDto.getReason()).build();
	}
}
