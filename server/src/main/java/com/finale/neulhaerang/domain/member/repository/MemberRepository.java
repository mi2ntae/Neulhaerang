package com.finale.neulhaerang.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finale.neulhaerang.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Long, Member> {

}
