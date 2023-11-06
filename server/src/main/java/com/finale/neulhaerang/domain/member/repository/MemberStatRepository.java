package com.finale.neulhaerang.domain.member.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.finale.neulhaerang.domain.member.document.StatRecord;
import com.finale.neulhaerang.domain.routine.entity.StatType;

public interface MemberStatRepository extends MongoRepository<StatRecord, Long> {
	List<StatRecord> findStatRecordsByStatTypeIsInAndMemberId(List<StatType> statType, long memberId);

	List<StatRecord> findStatRecordsByStatTypeIsNotInAndMemberId(List<StatType> ignoreStats, long memberId);

	List<StatRecord> findStatRecordsByStatTypeAndMemberId(StatType statType, long memberId);

	Page<StatRecord> findStatRecordsByStatTypeAndMemberIdOrderByRecordedDateDesc(StatType statType, long memberId, Pageable page);
}
