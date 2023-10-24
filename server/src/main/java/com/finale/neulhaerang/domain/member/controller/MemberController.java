package com.finale.neulhaerang.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finale.neulhaerang.domain.member.dto.response.MemberStatusResDto;
import com.finale.neulhaerang.domain.member.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(value = "유저 API", tags = {"User"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {
	private final MemberService memberService;

	@ApiOperation(value = "유저 프로필 조회", notes = "유저 프로필 조회")
	@GetMapping("/status/{memberId}")
	public ResponseEntity<MemberStatusResDto> findStatusByMemberId(@PathVariable long memberId) {
		return ResponseEntity.ok().body(memberService.findStatusByMemberId(memberId));
	}

}
