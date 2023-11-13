package com.finale.neulhaerang.domain.ar.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.finale.neulhaerang.domain.ar.dto.request.AroundMemberCharacterReqDto;
import com.finale.neulhaerang.domain.ar.dto.response.AroundMemberCharacterListResDto;
import com.finale.neulhaerang.domain.member.document.StatRecord;
import com.finale.neulhaerang.domain.member.dto.request.StatRecordReqDto;
import com.finale.neulhaerang.domain.member.entity.CharacterInfo;
import com.finale.neulhaerang.domain.member.entity.Device;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.member.repository.CharacterInfoRepository;
import com.finale.neulhaerang.domain.member.repository.DeviceRepository;
import com.finale.neulhaerang.domain.member.repository.MemberRepository;
import com.finale.neulhaerang.domain.member.repository.MemberStatRepository;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.repository.EarnedTitleRepository;
import com.finale.neulhaerang.global.event.RepelIndolenceMonsterEvent;
import com.finale.neulhaerang.global.event.TagOtherMemberEvent;
import com.finale.neulhaerang.global.exception.ar.InvalidTagException;
import com.finale.neulhaerang.global.exception.member.NotExistDeviceException;
import com.finale.neulhaerang.global.exception.member.NotExistMemberException;
import com.finale.neulhaerang.global.handler.AuthenticationHandler;
import com.finale.neulhaerang.global.util.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArServiceImpl implements ArService {
	private final EarnedTitleRepository earnedTitleRepository;
	private final MemberStatRepository memberStatRepository;
	private final MemberRepository memberRepository;
	private final DeviceRepository deviceRepository;
	private final CharacterInfoRepository characterInfoRepository;
	private final RedisUtil redisUtil;

	private final AuthenticationHandler authenticationHandler;
	private final ApplicationEventPublisher publisher;
	private final long SOCIAL_FIRST_TAG = 31;
	private final long REPEL_INDOLENCE_MONSTER = 20;

	@Override
	public boolean tagOtherMember(long memberId) throws NotExistMemberException, InvalidTagException {
		Optional<Member> optionalMember = memberRepository.findById(memberId);
		if (optionalMember.isEmpty()) {
			throw new NotExistMemberException();
		}
		long loginMemberId = authenticationHandler.getLoginMemberId();
		if (optionalMember.get().getId() == loginMemberId) {
			throw new InvalidTagException();
		}

		LocalDateTime now = LocalDateTime.now();
		List<StatRecord> statRecords = memberStatRepository.findStatRecordsByMemberIdAndReasonContaining(loginMemberId,
			"[ID : " + memberId + ", 닉네임 : " + optionalMember.get().getNickname() + "]");
		boolean isTagedToday = statRecords.stream()
			.filter(
				record -> record.getRecordedDate().minusHours(9).format(DateTimeFormatter.ISO_DATE).equals(now.format(
					DateTimeFormatter.ISO_DATE)))
			.count() == 0 ? false : true;

		if (!isTagedToday) {
			StatRecordReqDto statRecordReqDto = StatRecordReqDto.of(
				"소셜에서 [ID : " + memberId + ", 닉네임 : " + optionalMember.get().getNickname() + "] 사용자를 태그",
				LocalDateTime.now(), StatType.인싸력, 3);
			memberStatRepository.save(StatRecord.of(statRecordReqDto, loginMemberId));
		} else {
			log.debug(loginMemberId + " 사용자가 오늘 이미 " + optionalMember.get().getId() + " 사용자를 태그하여 인싸력이 올라가지 않았습니다.");
		}

		Optional<EarnedTitle> optionalEarnedTitle = earnedTitleRepository.findEarnedTitleByMember_IdAndTitle_Id(
			loginMemberId, SOCIAL_FIRST_TAG);
		if (optionalEarnedTitle.isEmpty()) {
			publisher.publishEvent(new TagOtherMemberEvent(memberRepository.getReferenceById(loginMemberId)));
			return true;
		}
		return false;
	}

	@Override
	public boolean repelIndolenceMonster() {
		long loginMemberId = authenticationHandler.getLoginMemberId();
		List<StatRecord> statRecords = memberStatRepository.findStatRecordsByStatTypeAndMemberId(StatType.나태도,
			loginMemberId);
		LocalDateTime now = LocalDateTime.now();
		int indolenceSum = statRecords.stream()
			.map(StatRecord::getWeight)
			.reduce(0, Integer::sum);

		if (indolenceSum > 50) {
			StatRecordReqDto statRecordReqDto = StatRecordReqDto.of("나태 괴물 처치!", now, StatType.나태도, -50);
			memberStatRepository.save(StatRecord.of(statRecordReqDto, loginMemberId));
		}

		Optional<EarnedTitle> optionalEarnedTitle = earnedTitleRepository.findEarnedTitleByMember_IdAndTitle_Id(
			loginMemberId, REPEL_INDOLENCE_MONSTER);
		if (optionalEarnedTitle.isEmpty()) {
			publisher.publishEvent(new RepelIndolenceMonsterEvent(memberRepository.findById(loginMemberId).get()));
			return true;
		}
		return false;
	}

	@Override
	public List<AroundMemberCharacterListResDto> findAroundMemberByLocation(
		AroundMemberCharacterReqDto aroundMemberCharacterReqDto) {
		List<String> deviceIds = redisUtil.changeGeo(new Point(aroundMemberCharacterReqDto.getLongitude(), aroundMemberCharacterReqDto.getLatitude())
			, authenticationHandler.getLoginDeviceId());
		List<AroundMemberCharacterListResDto> aroundMemberCharacterListResDtos = new ArrayList<>();
		deviceIds.forEach(deviceId -> {
			Optional<Device> device = deviceRepository.findDeviceByDeviceToken(deviceId);
			if(device.isEmpty()) {
				throw new NotExistDeviceException();
			}
			long memberId = device.get().getMember().getId();
			if(memberId != authenticationHandler.getLoginMemberId()) {
				Optional<CharacterInfo> characterInfo = characterInfoRepository.findCharacterInfoByMember_Id(memberId);
				aroundMemberCharacterListResDtos.add(AroundMemberCharacterListResDto.from(characterInfo.get()));
			}
		});
		return aroundMemberCharacterListResDtos;
	}
}
