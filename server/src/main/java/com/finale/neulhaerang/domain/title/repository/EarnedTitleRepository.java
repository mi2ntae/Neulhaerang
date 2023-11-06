package com.finale.neulhaerang.domain.title.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finale.neulhaerang.domain.title.entity.EarnedTitle;

public interface EarnedTitleRepository extends JpaRepository<EarnedTitle, Long> {
	Optional<EarnedTitle> findEarnedTitleByMember_IdAndTitle_Id(long memberId, long titleId);
}
