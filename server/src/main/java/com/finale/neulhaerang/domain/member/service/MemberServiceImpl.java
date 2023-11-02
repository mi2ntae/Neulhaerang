package com.finale.neulhaerang.domain.member.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.finale.neulhaerang.domain.member.document.MemberStat;
import com.finale.neulhaerang.domain.member.dto.response.MemberCharacterResDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberStatusResDto;
import com.finale.neulhaerang.domain.member.entity.CharacterInfo;
import com.finale.neulhaerang.domain.member.entity.Device;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.CharacterInfoRepository;
import com.finale.neulhaerang.domain.member.repository.DeviceRepository;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.member.repository.MemberStatRepository;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.global.exception.member.NotExistCharacterInfoException;
import com.finale.neulhaerang.global.exception.member.NotExistDeviceException;
import com.finale.neulhaerang.global.exception.member.NotExistMemberException;
import com.finale.neulhaerang.global.util.AuthenticationHandler;
import com.finale.neulhaerang.global.util.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	private final MemberRepository memberRepository;
	private final DeviceRepository deviceRepository;
	private final CharacterInfoRepository characterInfoRepository;
	private final MemberStatRepository memberStatRepository;
	private final RedisUtil redisUtil;

	private final AuthenticationHandler authenticationHandler;

	// 멤버 상태 정보 조회 (나태도, 피로도) -> MongoDB에서 조회
	@Override
	public MemberStatusResDto findStatusByMemberId(long memberId) {
		log.info("MongoDB로부터 Member의 나태도, 피로도 조회.");
		Optional<MemberStat> optionalMemberStat = memberStatRepository.findMemberStatByMemberId(memberId);
		if(!optionalMemberStat.isPresent()) throw new NotExistMemberException();

		int sumOfIndolence = optionalMemberStat.get().getRecords().stream()
			.filter(record -> record.getStatType().equals(StatType.나태도))
			.filter(record -> record.getRecordedDate().minusHours(9).format(DateTimeFormatter.ISO_DATE).equals(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)))
			.map(record -> record.getWeight()).reduce(0, Integer::sum);
		int sumOfTiredness = optionalMemberStat.get().getRecords().stream()
			.filter(record -> record.getStatType().equals(StatType.피곤도))
			.filter(record -> record.getRecordedDate().minusHours(9).format(DateTimeFormatter.ISO_DATE).equals(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)))
			.map(record -> record.getWeight()).reduce(0, Integer::sum);
		return MemberStatusResDto.create(sumOfIndolence, sumOfTiredness);
	}

	// MongoDB에 스탯 업데이트하는 예제 코드 : 추후 변경해서 사용
	@Override
	public void testInsert(MemberStat memberStat) {
		Optional<MemberStat> optionalMemberStat = memberStatRepository.findMemberStatByMemberId(memberStat.getMemberId());
		MemberStat memberStat2;
		if(optionalMemberStat.isEmpty()) {
			memberStat2 = MemberStat.create(memberStat.getMemberId());
		} else {
			memberStat2 = optionalMemberStat.get();
		}
		memberStat2.addRecord(memberStat.getRecords().get(0));
		memberStatRepository.save(memberStat2);
	}

	// 멤버 캐릭터 조회
	@Override
	public MemberCharacterResDto findCharacterByMemberId(long memberId) throws NotExistCharacterInfoException {
		log.info("캐릭터 타입, 스킨, 의상 정보를 조회.");
		Optional<CharacterInfo> optionalCharacterInfo = characterInfoRepository.findCharacterInfoByMember_Id(memberId);
		if(optionalCharacterInfo.isEmpty()) {
			throw new NotExistCharacterInfoException();
		}
		return MemberCharacterResDto.from(optionalCharacterInfo.get());
	}

	@Override
	public Member loadMemberByDeviceToken(String deviceToken) throws NotExistDeviceException, NotExistMemberException {
		log.info("받아온 토큰으로 Auth 생성을 위한 멤버 조회");
		Optional<Device> optionalDevice = deviceRepository.findDeviceByDeviceToken(deviceToken);
		if(optionalDevice.isEmpty()) {
			throw new NotExistDeviceException();
		}
		Optional<Member> optionalMember = memberRepository.findMemberByIdAndWithdrawalDateIsNull(optionalDevice.get().getMember().getId());
		if(optionalMember.isEmpty()) {
			throw new NotExistMemberException();
		}
		return optionalMember.get();
	}

	@Transactional
	@Override
	public void removeMember() throws NotExistMemberException {
		long memberId = authenticationHandler.getLoginMemberId();
		Optional<Member> optionalMember = memberRepository.findMemberByIdAndWithdrawalDateIsNull(memberId);
		if(optionalMember.isEmpty()) {
			throw new NotExistMemberException();
		}

		Member member = optionalMember.get();
		member.updateWithdrawalDate(LocalDateTime.now());

		List<Device> devices = deviceRepository.findDevicesByMember_Id(member.getId());
		devices.stream().forEach((device) -> {
			redisUtil.deleteData(device.getDeviceToken());
			deviceRepository.delete(device);
		});
	}
}
