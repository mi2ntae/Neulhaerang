package com.finale.neulhaerang.domain.member.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.finale.neulhaerang.domain.member.document.StatRecord;
import com.finale.neulhaerang.domain.routine.entity.StatType;

public interface MemberStatRepository extends MongoRepository<StatRecord, Long> {
	List<StatRecord> findStatRecordsByStatTypeIsIn(List<StatType> statType);

	List<StatRecord> findStatRecordsByStatTypeIsNotIn(List<StatType> ignoreStats);

	List<StatRecord> findStatRecordsByStatType(StatType statType);

	Page<StatRecord> findStatRecordsByStatTypeOrderByRecordedDateDesc(StatType statType, Pageable page);
}
