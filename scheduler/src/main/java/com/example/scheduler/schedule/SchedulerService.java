package com.example.scheduler.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SchedulerService {
	private static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Async
	@Scheduled(cron = "*/2 * * * * *") // 매 2초마다 Task 수행
	@SchedulerLock(name = "schedulerStart1", lockAtMostFor = "PT10S", lockAtLeastFor = "PT10S")
	public void schedulerStart1() throws InterruptedException {
		log.info("################ Hello! scheduler 1 - {}", LocalDateTime.now().format(PATTERN));
		Thread.sleep(5000);
		log.info("################ threadName - {}", Thread.currentThread().getName());

	}

	@Scheduled(cron = "*/5 * * * * *") // 매 5초마다 Task 수행
	// @SchedulerLock(name = "schedulerStart2", lockAtMostFor = "PT10S", lockAtLeastFor = "PT10S")
	public void schedulerStart2() throws InterruptedException {
		log.info("@@@@@@@@@@@@@@@ Hello! scheduler 2 - {}", LocalDateTime.now().format(PATTERN));
		Thread.sleep(5000);
	}

	@Scheduled(cron = "*/5 * * * * *") // 매 5초마다 Task 수행
	public void schedulerStart3() throws InterruptedException {
		log.info("$$$$$$$$$$$$$$$$ Hello! scheduler 3 - {}", LocalDateTime.now().format(PATTERN));
		Thread.sleep(5000);
	}
}
