package com.finale.neulhaerang.global.util;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.global.exception.common.AccessForbiddenException;
import com.finale.neulhaerang.global.exception.member.NonExistMemberException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationHandler {
	private final MemberRepository memberRepository;

	public void checkMemberAuthentication(long memberId) throws NonExistMemberException, AccessForbiddenException {
		long loginId = getLoginMemberId();
		Optional<Member> loginMember = memberRepository.findById(loginId);
		if(loginMember.isEmpty()) {
			throw new NonExistMemberException();
		}
		if(loginMember.get().getId() != memberId) {
			throw new AccessForbiddenException();
		}
	}

	public long getLoginDeviceId() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return Long.parseLong(userDetails.getUsername());
	}

	public long getLoginMemberId() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return Long.parseLong(userDetails.getPassword());
	}

}
