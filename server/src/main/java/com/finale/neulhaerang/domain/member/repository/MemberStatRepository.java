package com.finale.neulhaerang.domain.member.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.finale.neulhaerang.domain.member.document.MemberStat;

public interface MemberStatRepository extends MongoRepository<MemberStat, Long> {
	Optional<MemberStat> findMemberStatByMemberId(long memberId);
}
