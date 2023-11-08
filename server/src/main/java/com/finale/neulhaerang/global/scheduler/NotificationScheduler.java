package com.finale.neulhaerang.global.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;
import com.finale.neulhaerang.domain.todo.entity.Todo;
import com.finale.neulhaerang.domain.todo.repository.TodoRepository;
import com.finale.neulhaerang.global.notification.dto.request.RoutineNotificationReqDto;
import com.finale.neulhaerang.global.notification.dto.request.TodoNotificationReqDto;
import com.finale.neulhaerang.global.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationScheduler {
	private RoutineRepository routineRepository;
	private TodoRepository todoRepository;
	private NotificationService notificationService;

	@Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
	public void sendNotificationTrigger() {
		LocalTime now = LocalTime.now();
		LocalDate today = LocalDate.now();
		sentRoutineNotificationTrigger(now, today);
		sendTodoNotificationTrigger(now, today);
	}

	private void sendTodoNotificationTrigger(LocalTime now, LocalDate today) {
		log.info("------------- " + today + " " + now + " 루틴 알림 스케줄러 실행 -------------");
		List<Todo> notificationTodos = todoRepository.findTodosByStatusIsFalseAndTodoDateIsBetweenAndAlarmIsTrue(
			LocalDateTime.of(today, now.withSecond(0)), LocalDateTime.of(today, now.plusMinutes(1).withSecond(0)));
		notificationTodos.forEach(r ->
			{
				notificationService.sendNotificationByToken(r.getMember().getId(),
					TodoNotificationReqDto.create(r.getMember(), r));
				log.info(r.getMember().getNickname() + "님에게 투두 알림을 전송");
			}
		);
	}

	private void sentRoutineNotificationTrigger(LocalTime now, LocalDate today) {
		log.info("------------- " + today + " " + now + " 루틴 알림 스케줄러 실행 -------------");
		String dayOfVaule = calculateRepeated(today);
		List<Routine> notificationRoutines = routineRepository.findRoutinesByDayOfValueAndAlarmIsTrueAndAlarmTimeIsNotNull(
			dayOfVaule, today);

		notificationRoutines
			.stream()
			.filter(r -> r.getAlarmTime().equals(now))
			.forEach(r -> {
				notificationService.sendNotificationByToken(r.getMember().getId(),
					RoutineNotificationReqDto.create(r.getMember(), r));
				log.info(r.getMember().getNickname() + "님에게 루틴 알림을 전송");
			});
	}

	private String calculateRepeated(LocalDate today) {
		StringBuilder dayOfVaule = new StringBuilder("_______");
		int dayOfWeekValue = today.getDayOfWeek().getValue() - 1;
		dayOfVaule.setCharAt(dayOfWeekValue, '1');
		return dayOfVaule.toString();
	}
}
