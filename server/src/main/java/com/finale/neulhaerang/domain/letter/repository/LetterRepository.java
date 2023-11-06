package com.finale.neulhaerang.domain.letter.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finale.neulhaerang.domain.letter.entity.Letter;
import com.finale.neulhaerang.domain.member.entity.Member;

public interface LetterRepository extends JpaRepository<Letter, Long> {
	Optional<Letter> findLetterByMemberAndLetterDate(Member member, LocalDate letterDate);
}
