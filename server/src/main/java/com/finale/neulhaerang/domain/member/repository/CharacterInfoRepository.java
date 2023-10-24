package com.finale.neulhaerang.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finale.neulhaerang.domain.member.entity.CharacterInfo;

public interface CharacterInfoRepository extends JpaRepository<CharacterInfo, Long> {
	Optional<CharacterInfo> findCharacterInfoByMember_Id(long memberId);
}
