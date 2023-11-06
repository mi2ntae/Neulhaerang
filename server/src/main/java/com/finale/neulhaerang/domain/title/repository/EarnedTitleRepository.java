package com.finale.neulhaerang.domain.title.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.title.entity.EarnedTitle;

public interface EarnedTitleRepository extends JpaRepository<EarnedTitle, Long> {
	boolean existsByTitle_IdAndMember(Long titleId, Member member);
}
