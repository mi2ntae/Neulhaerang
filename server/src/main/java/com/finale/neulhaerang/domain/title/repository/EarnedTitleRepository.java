package com.finale.neulhaerang.domain.title.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finale.neulhaerang.domain.title.entity.EarnedTitle;

public interface EarnedTitleRepository extends JpaRepository<EarnedTitle, Long> {
}
