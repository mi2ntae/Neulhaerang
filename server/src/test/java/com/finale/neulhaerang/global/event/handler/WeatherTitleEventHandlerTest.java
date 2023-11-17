package com.finale.neulhaerang.global.event.handler;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.finale.neulhaerang.domain.routine.entity.DailyRoutine;
import com.finale.neulhaerang.domain.routine.entity.Routine;
import com.finale.neulhaerang.domain.routine.entity.StatType;
import com.finale.neulhaerang.domain.routine.repository.DailyRoutineRepository;
import com.finale.neulhaerang.domain.routine.repository.RoutineRepository;
import com.finale.neulhaerang.domain.title.entity.EarnedTitle;
import com.finale.neulhaerang.domain.title.entity.Title;
import com.finale.neulhaerang.domain.title.repository.EarnedTitleRepository;
import com.finale.neulhaerang.domain.title.repository.TitleRepository;
import com.finale.neulhaerang.domain.todo.entity.Todo;
import com.finale.neulhaerang.domain.todo.repository.TodoRepository;
import com.finale.neulhaerang.global.util.BaseTest;

class WeatherTitleEventHandlerTest extends BaseTest {
	@Autowired
	private DailyRoutineRepository dailyRoutineRepository;
	@Autowired
	private TodoRepository todoRepository;
	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private EarnedTitleRepository earnedTitleRepository;
	@Autowired
	private WeatherTitleEventHandler weatherTitleEventHandler;
	@Autowired
	private RoutineRepository routineRepository;

	@DisplayName("10일 연속 routine를 70퍼센트 이상 달성했다면 칭호를 1개 받습니다.")
	@Test
	void When_SunnyContinuous10Days_Expect_GetSunnyTitle() {
		// given
		member.updateCreateDate(LocalDateTime.now().minusDays(100));
		Title title1 = createTitle(25L, "해10", "태양10");
		Title title2 = createTitle(26L, "해50", "태양50");
		titleRepository.saveAll(List.of(title1, title2));
		Routine routine = Routine.builder()
			.content("hi")
			.member(member)
			.alarm(false)
			.statType(StatType.갓생력)
			.repeated("0000000")
			.build();
		Routine save = routineRepository.save(routine);
		for (int i = 1; i <= 10; i++) {
			createDailyRoutine(i, save);
		}

		// when
		weatherTitleEventHandler.checkIfGetWeatherTitle(member);
		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(1);
	}

	@DisplayName("9일전에 가입한 유저가 10일 연속 todo를 70퍼센트 이상 달성했다면 칭호를 0개 받습니다.")
	@Test
	void When_SunnyContinuous10DaysWithCreate9DaysAgo_Expect_NotGetSunnyTitle() {
		// given
		member.updateCreateDate(LocalDateTime.now().minusDays(9));
		Title title1 = createTitle(25L, "해10", "태양10");
		Title title2 = createTitle(26L, "해50", "태양50");
		titleRepository.saveAll(List.of(title1, title2));
		for (int i = 1; i <= 10; i++) {
			createCheckTodo(i);
		}

		// when
		weatherTitleEventHandler.checkIfGetWeatherTitle(member);
		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(0);
	}

	@DisplayName("9일 연속 todo를 70퍼센트 이상 달성했다면 칭호를 0개 받습니다.")
	@Test
	void When_SunnyContinuous9Days_Expect_GetSunnyTitle() {
		// given
		member.updateCreateDate(LocalDateTime.now().minusDays(100));
		Title title1 = createTitle(25L, "해10", "태양10");
		Title title2 = createTitle(26L, "해50", "태양50");
		titleRepository.saveAll(List.of(title1, title2));
		for (int i = 1; i <= 9; i++) {
			createCheckTodo(i);
		}

		// when
		weatherTitleEventHandler.checkIfGetWeatherTitle(member);
		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(0);
	}

	@DisplayName("49일 연속 todo를 70퍼센트 이상 달성했다면 칭호를 1개 받습니다.")
	@Test
	void When_SunnyContinuous49Days_Expect_GetSunnyTitle() {
		// given
		member.updateCreateDate(LocalDateTime.now().minusDays(100));
		Title title1 = createTitle(25L, "해10", "태양10");
		Title title2 = createTitle(26L, "해50", "태양50");
		titleRepository.saveAll(List.of(title1, title2));
		for (int i = 1; i <= 49; i++) {
			createCheckTodo(i);
		}

		// when
		weatherTitleEventHandler.checkIfGetWeatherTitle(member);
		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(1);
	}

	@DisplayName("50일 연속 todo를 70퍼센트 이상 달성했다면 칭호를 2개 받습니다.")
	@Test
	void When_SunnyContinuous50Days_Expect_GetSunnyTitle() {
		// given
		member.updateCreateDate(LocalDateTime.now().minusDays(100));
		Title title1 = createTitle(25L, "해10", "태양10");
		Title title2 = createTitle(26L, "해50", "태양50");
		titleRepository.saveAll(List.of(title1, title2));
		for (int i = 1; i <= 50; i++) {
			createCheckTodo(i);
		}

		// when
		weatherTitleEventHandler.checkIfGetWeatherTitle(member);
		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(2);
	}

	@DisplayName("9일 연속 todo를 70퍼센트 이하 40퍼센트 이상 달성했다면 칭호를 0개 받습니다.")
	@Test
	void When_SunnyContinuous9Days_Expect_GetCloudTitle() {
		// given
		member.updateCreateDate(LocalDateTime.now().minusDays(100));
		Title title1 = createTitle(27L, "구10", "구름10");
		Title title2 = createTitle(28L, "구50", "구름50");
		titleRepository.saveAll(List.of(title1, title2));
		for (int i = 1; i <= 9; i++) {
			createCheckTodo(i);
			createUncheckTodo(i);
		}

		// when
		weatherTitleEventHandler.checkIfGetWeatherTitle(member);
		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(0);
	}

	@DisplayName("49일 연속 todo를 70퍼센트 이하 40퍼센트 이상 달성했다면 칭호를 1개 받습니다.")
	@Test
	void When_SunnyContinuous49Days_Expect_GetCloudTitle() {
		// given
		member.updateCreateDate(LocalDateTime.now().minusDays(100));
		Title title1 = createTitle(27L, "구10", "구름10");
		Title title2 = createTitle(28L, "구50", "구름50");
		titleRepository.saveAll(List.of(title1, title2));
		for (int i = 1; i <= 49; i++) {
			createCheckTodo(i);
			createUncheckTodo(i);
		}

		// when
		weatherTitleEventHandler.checkIfGetWeatherTitle(member);
		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(1);
	}

	@DisplayName("50일 연속 todo를 70퍼센트 이하 40퍼센트 이상 달성했다면 칭호를 2개 받습니다.")
	@Test
	void When_SunnyContinuous50Days_Expect_GetCloudTitle() {
		// given
		member.updateCreateDate(LocalDateTime.now().minusDays(100));
		Title title1 = createTitle(27L, "구10", "구름10");
		Title title2 = createTitle(28L, "구50", "구름50");
		titleRepository.saveAll(List.of(title1, title2));
		for (int i = 1; i <= 50; i++) {
			createCheckTodo(i);
			createUncheckTodo(i);
		}

		// when
		weatherTitleEventHandler.checkIfGetWeatherTitle(member);
		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(2);
	}

	@DisplayName("9일 연속 todo를 40퍼센트 이하 달성했다면 칭호를 0개 받습니다.")
	@Test
	void When_SunnyContinuous9Days_Expect_GetRainTitle() {
		// given
		member.updateCreateDate(LocalDateTime.now().minusDays(9));
		Title title1 = createTitle(29L, "비10", "비10");
		Title title2 = createTitle(30L, "비50", "비50");
		titleRepository.saveAll(List.of(title1, title2));
		for (int i = 1; i <= 9; i++) {
			createUncheckTodo(i);
		}

		// when
		weatherTitleEventHandler.checkIfGetWeatherTitle(member);
		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(0);
	}

	@DisplayName("49일 연속 todo를 40퍼센트 이하 달성했다면 칭호를 1개 받습니다.")
	@Test
	void When_SunnyContinuous49Days_Expect_GetRainTitle() {
		// given
		member.updateCreateDate(LocalDateTime.now().minusDays(49));
		Title title1 = createTitle(29L, "비10", "비10");
		Title title2 = createTitle(30L, "비50", "비50");
		titleRepository.saveAll(List.of(title1, title2));
		for (int i = 1; i <= 49; i++) {
			createUncheckTodo(i);
		}

		// when
		weatherTitleEventHandler.checkIfGetWeatherTitle(member);
		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(1);
	}

	@DisplayName("50일 연속 todo를 40퍼센트 이하 달성했다면 칭호를 2개 받습니다.")
	@Test
	void When_SunnyContinuous50Days_Expect_GetRainTitle() {
		// given
		member.updateCreateDate(LocalDateTime.now().minusDays(100));
		Title title1 = createTitle(29L, "비10", "비10");
		Title title2 = createTitle(30L, "비50", "비50");
		titleRepository.saveAll(List.of(title1, title2));
		for (int i = 1; i <= 50; i++) {
			createUncheckTodo(i);
		}

		// when
		weatherTitleEventHandler.checkIfGetWeatherTitle(member);
		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(2);
	}

	@DisplayName("50일 연속 todo를 40퍼센트 이하 달성했다면 칭호를 2개 받습니다.")
	@Test
	void When_SunnyContinuous50DaysWithNothing_Expect_GetRainTitle() {
		// given
		member.updateCreateDate(LocalDateTime.now().minusDays(100));
		Title title1 = createTitle(29L, "비10", "비10");
		Title title2 = createTitle(30L, "비50", "비50");
		titleRepository.saveAll(List.of(title1, title2));

		// when
		weatherTitleEventHandler.checkIfGetWeatherTitle(member);
		// then
		List<EarnedTitle> earnedTitles = earnedTitleRepository.findAll();
		assertThat(earnedTitles).hasSize(2);
	}

	private Title createTitle(Long id, String name, String content) {
		return Title.builder()
			.id(id)
			.name(name)
			.content(content)
			.build();
	}

	public void createCheckTodo(int i) {
		Todo todo = Todo.builder()
			.member(member)
			.check(true)
			.todoDate(LocalDateTime.now().minusDays(i))
			.content("hi")
			.alarm(false)
			.status(false)
			.statType(StatType.갓생력)
			.build();
		todoRepository.save(todo);
	}

	public void createUncheckTodo(int i) {
		Todo todo = Todo.builder()
			.member(member)
			.check(false)
			.todoDate(LocalDateTime.now().minusDays(i))
			.content("hi")
			.alarm(false)
			.status(false)
			.statType(StatType.갓생력)
			.build();
		todoRepository.save(todo);
	}

	public void createDailyRoutine(int i, Routine routine) {
		DailyRoutine dailyRoutine = DailyRoutine.builder()
			.routineDate(LocalDate.now().minusDays(i))
			.routine(routine)
			.check(true)
			.status(false)
			.build();
		dailyRoutineRepository.save(dailyRoutine);
	}
}