package com.finale.neulhaerang.global.event.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.finale.neulhaerang.domain.member.document.StatRecord;
import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.global.event.StatEvent;

@Component
public class StatTitleEventHandler extends TitleEventHandler {
	private Map<StatType, Long[]> titleIdByStat = new HashMap<>() {
		{
			put(StatType.갓생력, new Long[] {1L, 2L, 3L});
			put(StatType.생존력, new Long[] {4L, 5L, 6L});
			put(StatType.인싸력, new Long[] {7L, 8L, 9L});
			put(StatType.튼튼력, new Long[] {10L, 11L, 12L});
			put(StatType.창의력, new Long[] {13L, 14L, 15L});
			put(StatType.최애력, new Long[] {16L, 17L, 18L});
		}
	};

	@Async
	@EventListener
	public void checkLifeStat(StatEvent statEvent) {
		checkIfGetTitleOrNot(statEvent.getMember(), StatType.갓생력);
	}

	@Async
	@EventListener
	public void checkSurvivalStat(StatEvent statEvent) {
		checkIfGetTitleOrNot(statEvent.getMember(), StatType.생존력);
	}

	@Async
	@EventListener
	public void checkPopularityStat(StatEvent statEvent) {
		checkIfGetTitleOrNot(statEvent.getMember(), StatType.인싸력);
	}

	@Async
	@EventListener
	public void checkPowerStat(StatEvent statEvent) {
		checkIfGetTitleOrNot(statEvent.getMember(), StatType.튼튼력);
	}

	@Async
	@EventListener
	public void checkCreativeStat(StatEvent statEvent) {
		checkIfGetTitleOrNot(statEvent.getMember(), StatType.창의력);
	}

	@Async
	@EventListener
	public void checkLoveStat(StatEvent statEvent) {
		checkIfGetTitleOrNot(statEvent.getMember(), StatType.최애력);
	}

	public void checkIfGetTitleOrNot(Member member, StatType statType) {
		Long[] titleId = titleIdByStat.get(statType);
		List<StatRecord> records = memberStatRepository.findStatRecordsByStatTypeIsInAndMemberId(List.of(statType),
			member.getId());

		int weight = records.stream()
			.map(record -> record.getWeight()).reduce(0, Integer::sum);

		if (weight >= 50) {
			getTitle(titleId[0], member);
		}
		if (weight >= 70) {
			getTitle(titleId[1], member);
		}
		if (weight >= 90) {
			getTitle(titleId[2], member);
		}
	}

}
