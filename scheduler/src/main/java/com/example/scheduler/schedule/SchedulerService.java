package com.example.scheduler.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SchedulerService {
	private static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Scheduled(cron = "*/5 * * * * *") // 매 5초마다 Task 수행
	@SchedulerLock(name = "schedulerStart1", lockAtMostFor = "PT10S", lockAtLeastFor = "PT10S")
	public void schedulerStart1() {
		log.info("################ Hello! scheduler 1 - {}", LocalDateTime.now().format(PATTERN));
	}

	@Scheduled(cron = "*/7 * * * * *") // 매 7초마다 Task 수행
	@SchedulerLock(name = "schedulerStart2", lockAtMostFor = "PT10S", lockAtLeastFor = "PT10S")
	public void schedulerStart2() {
		log.info("@@@@@@@@@@@@@@@ Hello! scheduler 2 - {}", LocalDateTime.now().format(PATTERN));
	}

	@Scheduled(cron = "*/10 * * * * *") // 매 20초마다 Task 수행
	public void schedulerStart3() {
		log.info("$$$$$$$$$$$$$$$$ Hello! scheduler 3 - {}", LocalDateTime.now().format(PATTERN));
	}
}
