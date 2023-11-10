package com.finale.neulhaerang.domain.member.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.finale.neulhaerang.domain.member.document.StatRecord;
import com.finale.neulhaerang.domain.member.dto.request.CharacterModifyReqDto;
import com.finale.neulhaerang.domain.member.dto.request.StatRecordReqDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberCharacterResDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberProfileResDto;
import com.finale.neulhaerang.domain.member.dto.response.MemberStatusResDto;
import com.finale.neulhaerang.domain.member.dto.response.StatListResDto;
import com.finale.neulhaerang.domain.member.dto.response.StatRecordListResDto;
import com.finale.neulhaerang.domain.member.entity.CharacterInfo;
import com.finale.neulhaerang.domain.member.entity.Device;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.CharacterInfoRepository;
import com.finale.neulhaerang.domain.member.repository.DeviceRepository;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.member.repository.MemberStatRepository;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.title.entity.Title;
import com.finale.neulhaerang.domain.title.repository.TitleRepository;
import com.finale.neulhaerang.global.exception.common.InValidPageIndexException;
import com.finale.neulhaerang.global.exception.member.InvalidStatKindException;
import com.finale.neulhaerang.global.exception.member.NotExistCharacterInfoException;
import com.finale.neulhaerang.global.exception.member.NotExistDeviceException;
import com.finale.neulhaerang.global.exception.member.NotExistMemberException;
import com.finale.neulhaerang.global.handler.AuthenticationHandler;
import com.finale.neulhaerang.global.util.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final DeviceRepository deviceRepository;
	private final CharacterInfoRepository characterInfoRepository;
	private final MemberStatRepository memberStatRepository;
	private final TitleRepository titleRepository;

	private final RedisUtil redisUtil;

	private final AuthenticationHandler authenticationHandler;

	private static final int PAGE_SIZE = 6;
	private static final int VALID_STAT_NUMS = 6;
	private static final int STAT_CHANGE_RECORD_DAYS = 7;

	// 스탯 점수에 따른 등급
	private int[] scores = new int[] {150, 120, 90, 60, 30};    // A+, A, B+, B, C+
	private String[] levels = new String[] {"A+", "A", "B+", "B", "C+", "C"};

	// 스탯 점수 총합에 따른 레벨업 경험치
	private static final int MAX_LEVEL = 20;
	private static final int LEVEL_WEIGHT = 15;

	// 멤버 상태 정보 조회 (나태도, 피로도) -> MongoDB에서 조회
	@Override
	public MemberStatusResDto findStatusByMemberId(long memberId) {
		log.info("MongoDB로부터 Member의 나태도, 피로도 조회.");
		Optional<Member> optionalMember = memberRepository.findById(memberId);
		if (optionalMember.isEmpty()) {
			throw new NotExistMemberException();
		}

		List<StatType> validStatTypes = new ArrayList<>();
		validStatTypes.add(StatType.나태도);
		validStatTypes.add(StatType.피곤도);
		List<StatRecord> records = memberStatRepository.findStatRecordsByStatTypeIsInAndMemberId(validStatTypes,
			memberId);

		LocalDateTime now = LocalDateTime.now();
		int sumOfIndolence = records.stream()
			.filter(r -> r.getStatType().equals(StatType.나태도))
			.filter(
				record -> record.getRecordedDate().minusHours(9).format(DateTimeFormatter.ISO_DATE).equals(now.format(
					DateTimeFormatter.ISO_DATE)))
			.map(record -> record.getWeight()).reduce(0, Integer::sum);
		int sumOfTiredness = records.stream()
			.filter(r -> r.getStatType().equals(StatType.피곤도))
			.filter(
				record -> record.getRecordedDate().minusHours(9).format(DateTimeFormatter.ISO_DATE).equals(now.format(
					DateTimeFormatter.ISO_DATE)))
			.map(record -> record.getWeight())
			.reduce(0, Integer::sum);

		return MemberStatusResDto.create(sumOfIndolence, sumOfTiredness);
	}

	// MongoDB에 스탯 업데이트
	@Override
	@Transactional
	public void createStat(StatRecordReqDto statRecordReqDto) {
		log.info("MongoDB에 스탯 내역 추가");
		Optional<Member> optionalMember = memberRepository.findById(authenticationHandler.getLoginMemberId());
		if (optionalMember.isEmpty()) {
			throw new NotExistMemberException();
		}
		memberStatRepository.save(StatRecord.of(statRecordReqDto, optionalMember.get().getId()));
	}

	// 멤버 캐릭터 조회
	@Override
	public MemberCharacterResDto findCharacterByMemberId(long memberId) throws NotExistCharacterInfoException {
		log.info("캐릭터 타입, 스킨, 의상 정보를 조회.");
		Optional<CharacterInfo> optionalCharacterInfo = characterInfoRepository.findCharacterInfoByMember_Id(memberId);
		if (optionalCharacterInfo.isEmpty()) {
			throw new NotExistCharacterInfoException();
		}
		return MemberCharacterResDto.from(optionalCharacterInfo.get());
	}

	@Override
	public Member loadMemberByDeviceToken(String deviceToken) throws NotExistDeviceException, NotExistMemberException {
		log.info("받아온 토큰으로 Auth 생성을 위한 멤버 조회");
		Optional<Device> optionalDevice = deviceRepository.findDeviceByDeviceToken(deviceToken);
		if (optionalDevice.isEmpty()) {
			throw new NotExistDeviceException();
		}
		Optional<Member> optionalMember = memberRepository.findMemberByIdAndWithdrawalDateIsNull(
			optionalDevice.get().getMember().getId());
		if (optionalMember.isEmpty()) {
			throw new NotExistMemberException();
		}
		return optionalMember.get();
	}

	@Transactional
	@Override
	public void removeMember() throws NotExistMemberException {
		long memberId = authenticationHandler.getLoginMemberId();
		Optional<Member> optionalMember = memberRepository.findMemberByIdAndWithdrawalDateIsNull(memberId);
		if (optionalMember.isEmpty()) {
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
		Optional<Member> optionalMember = memberRepository.findById(memberId);
		if (optionalMember.isEmpty()) {
			throw new NotExistMemberException();
		}

		List<StatType> ignoreTypes = new ArrayList<>();
		ignoreTypes.add(StatType.나태도);
		ignoreTypes.add(StatType.피곤도);
		List<StatRecord> records = memberStatRepository.findStatRecordsByStatTypeIsNotInAndMemberId(ignoreTypes,
			memberId);

		LocalDateTime now = LocalDateTime.now();
		int[] stats = new int[VALID_STAT_NUMS];
		records.stream()
			.filter(
				record -> record.getRecordedDate().minusHours(9).format(DateTimeFormatter.ISO_DATE).equals(now.format(
					DateTimeFormatter.ISO_DATE)))
			.forEach(record -> stats[record.getStatType().ordinal()] += record.getWeight());

		List<StatListResDto> statListResDtos = new ArrayList<>();
		Arrays.stream(stats).forEach(stat -> statListResDtos.add(StatListResDto.of(stat, getLevelByScore(stat))));
		return statListResDtos;
	}

	@Override
	public int[] findStatChangeRecordLastDaysByStatType(int statNo) throws
		InvalidStatKindException,
		NotExistMemberException {
		if (statNo < 0 || statNo >= VALID_STAT_NUMS) {
			throw new InvalidStatKindException();
		}

		Optional<Member> optionalMember = memberRepository.findById(authenticationHandler.getLoginMemberId());
		if (optionalMember.isEmpty()) {
			throw new NotExistMemberException();
		}

		List<StatRecord> records = memberStatRepository.findStatRecordsByStatTypeAndMemberId(StatType.values()[statNo],
			optionalMember.get().getId());
		LocalDateTime now = LocalDateTime.now();

		int[] changeRecords = new int[STAT_CHANGE_RECORD_DAYS + 1];

		records.forEach(record -> {
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
		for (int i = 0; i < STAT_CHANGE_RECORD_DAYS - 1; i++) {
			changeRecords[i + 1] += changeRecords[i];
		}
		return Arrays.copyOfRange(changeRecords, 0, STAT_CHANGE_RECORD_DAYS);
	}

	@Override
	public List<StatRecordListResDto> findStatChangeRecordByStatType(int statNo, int page) throws
		InvalidStatKindException,
		NotExistMemberException {
		if (statNo < 0 || statNo >= VALID_STAT_NUMS) {
			throw new InvalidStatKindException();
		}

		Optional<Member> optionalMember = memberRepository.findById(authenticationHandler.getLoginMemberId());
		if (optionalMember.isEmpty()) {
			throw new NotExistMemberException();
		}

		if (page < 0) {
			throw new InValidPageIndexException();
		}

		Page<StatRecord> records = memberStatRepository.findStatRecordsByStatTypeAndMemberIdOrderByRecordedDateDesc(
			StatType.values()[statNo], optionalMember.get().getId(),
			PageRequest.of(page, PAGE_SIZE));
		return records.stream().map(StatRecordListResDto::from).collect(Collectors.toList());
	}

	@Override
	public MemberProfileResDto findMemberProfileByMemberId(long memberId) throws NotExistMemberException {
		Optional<Member> optionalMember = memberRepository.findById(memberId);
		if (optionalMember.isEmpty()) {
			throw new NotExistMemberException();
		}

		List<StatType> ignoreTypes = new ArrayList<>();
		ignoreTypes.add(StatType.나태도);
		ignoreTypes.add(StatType.피곤도);
		List<StatRecord> records = memberStatRepository.findStatRecordsByStatTypeIsNotInAndMemberId(ignoreTypes,
			memberId);

		int sumExp = records.stream().map(record -> record.getWeight()).reduce(0, Integer::sum);
		int level = 1;
		while (level < MAX_LEVEL && sumExp - LEVEL_WEIGHT * level >= 0) {
			sumExp -= LEVEL_WEIGHT * level++;
		}

		Member member = optionalMember.get();
		StringBuilder titleBuilder = new StringBuilder();
		if (member.getTitleId() != 0) {
			Optional<Title> optionalTitle = titleRepository.findById(member.getTitleId());
			if (optionalTitle.isEmpty()) {
				titleBuilder.append("Unknown");
			} else {
				titleBuilder.append(optionalTitle.get().getName());
			}
		}

		if (level == MAX_LEVEL) {
			return MemberProfileResDto.of(MAX_LEVEL, 999, 999, titleBuilder.toString(), member.getNickname());
		}
		return MemberProfileResDto.of(level, LEVEL_WEIGHT * level, sumExp, titleBuilder.toString(),
			member.getNickname());
	}

	@Override
	public void modifyCharacterInfoByMember(CharacterModifyReqDto characterModifyReqDto) {
		
	}

	private String getLevelByScore(int score) {
		for (int i = 0; i < scores.length; i++) {
			if (score >= scores[i]) {
				return levels[i];
			}
		}
		return levels[levels.length - 1];
	}
}
