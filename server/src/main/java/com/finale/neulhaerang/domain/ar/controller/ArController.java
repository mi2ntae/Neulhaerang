package com.finale.neulhaerang.domain.ar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finale.neulhaerang.domain.ar.dto.request.AroundMemberCharacterReqDto;
import com.finale.neulhaerang.domain.ar.dto.response.AroundMemberCharacterListResDto;
import com.finale.neulhaerang.domain.ar.service.ArService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(value = "AR API", tags = {"AR"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/ar")
@Slf4j
public class ArController {
	private final ArService arService;

	@ApiOperation(value = "다른 사용자 태그", notes = "AR에서 다른 사용자를 태그")
	@PostMapping("/tag/{memberId}")
	public ResponseEntity<Boolean> tagOtherMember(@PathVariable long memberId) {
		return ResponseEntity.status(HttpStatus.OK).body(arService.tagOtherMember(memberId));
	}

	@ApiOperation(value = "나태 괴물 처치", notes = "AR에서 나태 괴물을 처치")
	@PostMapping("/indolence")
	public ResponseEntity<Boolean> repelIndolenceMonster() {
		return ResponseEntity.status(HttpStatus.OK).body(arService.repelIndolenceMonster());
	}

	@ApiOperation(value = "주변 사용자 찾기", notes = "AR에서 주변 사용자를 보여주기 위한 프로필 조회")
	@PatchMapping("/around")
	public ResponseEntity<List<AroundMemberCharacterListResDto>> findAroundMemberByLocation(@RequestBody AroundMemberCharacterReqDto aroundMemberCharacterReqDto) {
		return ResponseEntity.status(HttpStatus.OK).body(arService.findAroundMemberByLocation(aroundMemberCharacterReqDto));
	}
}
