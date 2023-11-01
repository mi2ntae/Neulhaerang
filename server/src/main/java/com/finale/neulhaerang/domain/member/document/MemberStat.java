package com.finale.neulhaerang.domain.member.document;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;

@Document(collection = "member_stat")
@Builder
@Getter
public class MemberStat {
	@Id
	private ObjectId _id;
	private long memberId;
	private List<StatRecord> records;

	public void addRecord(StatRecord statRecord){
		statRecord.updateMongoTime();
		this.records.add(statRecord);
	}

	public static MemberStat create(long memberId) {
		List<StatRecord> statRecords = new ArrayList<>();
		return MemberStat.builder()
			.memberId(memberId)
			.records(statRecords).build();
	}
}
