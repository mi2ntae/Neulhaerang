package com.finale.neulhaerang.domain.title.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finale.neulhaerang.domain.title.entity.Title;

public interface TitleRepository extends JpaRepository<Title, Long> {
}
