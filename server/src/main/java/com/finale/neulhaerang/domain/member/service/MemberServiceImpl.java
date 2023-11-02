package com.finale.neulhaerang.domain.member.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.finale.neulhaerang.domain.member.document.MemberStat;
import com.finale.neulhaerang.domain.member.document.StatRecord;
import com.finale.neulhaerang.domain.member.dto.response.MemberCharacterResDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberStatusResDto;
import com.finale.neulhaerang.domain.member.dto.response.StatChangeRecordResDto;
import com.finale.neulhaerang.domain.member.dto.response.StatListResDto;
import com.finale.neulhaerang.domain.member.entity.CharacterInfo;
import com.finale.neulhaerang.domain.member.entity.Device;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.CharacterInfoRepository;
import com.finale.neulhaerang.domain.member.repository.DeviceRepository;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.member.repository.MemberStatRepository;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.global.exception.member.InvalidStatKindException;
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

	private static final int VALID_STAT_NUMS = 6;
	private static final int STAT_CHANGE_RECORD_DAYS = 7;
	private int[] scores = new int[] {90,80,70,60,50};	// A+, A, B+, B, C+
	private String[] levels = new String[] {"A+", "A", "B+", "B", "C+", "C"};

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

	// MongoDB에 스탯 업데이트
	@Override
	public void createStat(StatRecord statRecord) {
		log.info("MongoDB에 스탯 내역 추가");
		Optional<MemberStat> optionalMemberStat = memberStatRepository.findMemberStatByMemberId(authenticationHandler.getLoginMemberId());
		MemberStat memberStat;
		if(optionalMemberStat.isEmpty()) {
			memberStat = MemberStat.create(authenticationHandler.getLoginMemberId());
		} else {
			memberStat = optionalMemberStat.get();
		}
		memberStat.addRecord(statRecord);
		memberStatRepository.save(memberStat);
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

	@Override
	public List<StatListResDto> findAllStatsByMemberId(long memberId) throws NotExistMemberException {
		Optional<MemberStat> optionalMemberStat = memberStatRepository.findMemberStatByMemberId(memberId);
		if(!optionalMemberStat.isPresent()) {
			throw new NotExistMemberException();
		}

		MemberStat memberStat = optionalMemberStat.get();
		int[] stats = new int[VALID_STAT_NUMS];
		memberStat.getRecords().stream()
			.filter(record -> !record.getStatType().equals(StatType.피곤도) && !record.getStatType().equals(StatType.나태도))
			.forEach(record -> stats[record.getStatType().ordinal()] += record.getWeight());

		List<StatListResDto> statListResDtos = new ArrayList<>();
		Arrays.stream(stats).forEach(stat -> statListResDtos.add(StatListResDto.builder()
			.score(stat)
			.level(getLevelByScore(stat)).build())
		);
		return statListResDtos;
	}

	@Override
	public StatChangeRecordResDto findStatChangeRecordLastDaysByStatNo(int statKind) throws InvalidStatKindException, NotExistMemberException{
		if(statKind < 0 || statKind >= VALID_STAT_NUMS) {
			throw new InvalidStatKindException();
		}

		Optional<MemberStat> optionalMemberStat = memberStatRepository.findMemberStatByMemberId(authenticationHandler.getLoginMemberId());
		if(optionalMemberStat.isEmpty()) {
			throw new NotExistMemberException();
		}

		MemberStat memberStat = optionalMemberStat.get();
		LocalDateTime now = LocalDateTime.now();

		int[] changeRecords = new int[STAT_CHANGE_RECORD_DAYS+1];

		memberStat.getRecords().stream().filter(r -> r.getStatType().ordinal() == statKind)
			.forEach(record -> {
				if (record.getRecordedDate()
					.toLocalDate()
					.isBefore(now.minusDays(STAT_CHANGE_RECORD_DAYS).toLocalDate())) {
					changeRecords[STAT_CHANGE_RECORD_DAYS] += record.getWeight();
				} else {
					for (int k = STAT_CHANGE_RECORD_DAYS; k > 0; k--) {
						if (record.getRecordedDate().toLocalDate().isEqual(now.minusDays(k).toLocalDate())) {
							changeRecords[STAT_CHANGE_RECORD_DAYS - k] += record.getWeight();
						}
					}
				}
			});

		changeRecords[0] += changeRecords[STAT_CHANGE_RECORD_DAYS];
		for(int i = 0; i < STAT_CHANGE_RECORD_DAYS-1; i++) {
			changeRecords[i+1] += changeRecords[i];
		}
		return StatChangeRecordResDto.from(Arrays.copyOfRange(changeRecords, 0, STAT_CHANGE_RECORD_DAYS));
	}

	private String getLevelByScore(int score) {
		for(int i = 0; i < scores.length; i++) {
			if(score >= scores[i]) {
				return levels[i];
			}
		}
		return levels[levels.length-1];
	}
}
