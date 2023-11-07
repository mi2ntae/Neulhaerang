package com.finale.neulhaerang.global.event.handler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.finale.neulhaerang.domain.member.entity.Member;
import com.finale.neulhaerang.domain.routine.repository.DailyRoutineRepository;
import com.finale.neulhaerang.domain.todo.repository.TodoRepository;
import com.finale.neulhaerang.global.event.WeatherEvent;

@Component
public class WeatherTitleEventHandler extends TitleEventHandler {
	@Autowired
	private TodoRepository todoRepository;
	@Autowired
	private DailyRoutineRepository dailyRoutineRepository;

	private Map<String, Long[]> titleIdByWeather = new HashMap<>() {
		{
			put("sunny", new Long[] {25L, 26L});
			put("cloud", new Long[] {27L, 28L});
			put("rain", new Long[] {29L, 30L});
		}
	};

	@Async
	@EventListener
	public void checkWeather(WeatherEvent weatherEvent) {
		LocalDate start = LocalDate.now().minusDays(1);
		LocalDate end = LocalDate.now().minusDays(51);
		if (end.isBefore(weatherEvent.getMember().getCreateDate().toLocalDate())) {
			end = weatherEvent.getMember().getCreateDate().toLocalDate();
		}

		List<String> weatherOfComplete = calculateWeatherOfDays(weatherEvent.getMember(), start, end);

		String todayWeather = weatherOfComplete.get(0);
		int countOfDay = 0;
		for (String weather : weatherOfComplete) {
			if (!weather.equals(todayWeather)) {
				return;
			}
			countOfDay++;
			if (countOfDay == 10) {
				getTitle(titleIdByWeather.get(todayWeather)[0], weatherEvent.getMember());
			}
		}
		if (countOfDay >= 50) {
			getTitle(titleIdByWeather.get(todayWeather)[1], weatherEvent.getMember());
		}

	}

	private List<String> calculateWeatherOfDays(Member member, LocalDate start, LocalDate end) {
		List<String> weatherOfComplete = new ArrayList<>();
		for (LocalDate date = start; !date.isAfter(end); date = date.minusDays(1)) {
			int totalTodo = todoRepository.findTodosByMemberAndStatusIsFalseAndTodoDateIsBetween(
				member,
				date.atStartOfDay(), date.atTime(
					LocalTime.MAX)).size();
			int totalRoutine = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutine_MemberAndStatusIsFalse(
				date, member).size();
			int checkTodo = todoRepository.findTodosByMemberAndStatusIsFalseAndCheckIsTrueAndTodoDateIsBetween(
				member,
				date.atStartOfDay(), date.atTime(LocalTime.MAX)).size();
			int checkRoutine = dailyRoutineRepository.findDailyRoutinesByRoutineDateAndRoutine_MemberAndStatusIsFalseAndCheckIsTrue(
				date, member).size();

			double ratio = (double)(checkTodo + checkRoutine) / (totalTodo + totalRoutine);
			if (Double.isNaN(ratio)) {
				ratio = 0;
			}
			if (ratio >= 70) {
				weatherOfComplete.add("sunny");
			} else if (ratio >= 40) {
				weatherOfComplete.add("cloud");
			} else {
				weatherOfComplete.add("rain");
			}
		}
		return weatherOfComplete;
	}
}
