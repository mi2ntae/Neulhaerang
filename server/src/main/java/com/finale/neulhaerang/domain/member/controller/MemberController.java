package com.finale.neulhaerang.domain.member.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finale.neulhaerang.domain.member.document.StatRecord;
import com.finale.neulhaerang.domain.member.dto.response.MemberCharacterResDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberStatusResDto;
import com.finale.neulhaerang.domain.member.dto.response.StatListResDto;
import com.finale.neulhaerang.domain.member.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(value = "유저 API", tags = {"Member"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {
	private final MemberService memberService;

	@ApiOperation(value = "멤버 상태 조회", notes = "멤버 상태 정보 조회")
	@GetMapping("/status/{memberId}")
	public ResponseEntity<MemberStatusResDto> findStatusByMemberId(@PathVariable long memberId) {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.findStatusByMemberId(memberId));
	}

	@ApiOperation(value = "멤버 캐릭터 조회", notes = "멤버 캐릭터 정보 조회")
	@GetMapping("/character/{memberId}")
	public ResponseEntity<MemberCharacterResDto> findCharacterByMemberId(@PathVariable long memberId) {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.findCharacterByMemberId(memberId));
	}

	@ApiOperation(value = "회원 탈퇴", notes = "로그인한 회원 탈퇴")
	@PatchMapping("/withdrawl")
	public ResponseEntity<Void> removeMember() {
		memberService.removeMember();
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@ApiOperation(value = "멤버 스탯 전체 조회", notes = "멤버 전체 스탯을 조회")
	@GetMapping("/stat/{memberId}")
	public ResponseEntity<List<StatListResDto>> findAllStatsByMemberId(@PathVariable long memberId) {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.findAllStatsByMemberId(memberId));
	}

	@ApiOperation(value = "멤버 특정 스탯 동향 조회", notes = "멤버 특정 스탯 동향 조회")
	@GetMapping("/stat")
	public ResponseEntity<int[]> findStatChangeRecordLastDaysByStatType(@RequestParam int statType) {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.findStatChangeRecordLastDaysByStatType(statType));
	}

	// MongoDB에 스탯 업데이트하는 예제 코드 : 추후 변경해서 사용
	@ApiOperation(value = "테스트", notes = "Mongo 테스트")
	@PostMapping("/mongo")
	public ResponseEntity<Void> mongo(@RequestBody StatRecord statRecord) {
		memberService.createStat(statRecord);
		return ResponseEntity.status(HttpStatus.OK).build();
	}


}
