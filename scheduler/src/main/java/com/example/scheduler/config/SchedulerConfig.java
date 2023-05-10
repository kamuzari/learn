package com.example.scheduler.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;

@EnableSchedulerLock(defaultLockAtMostFor = "PT30S", defaultLockAtLeastFor = "PT30S")
@EnableScheduling
@Configuration
public class SchedulerConfig {
	/**
	 * replication 환경일 때는 마스터만 넣어야 함.
	 * @param dataSource
	 * @return
	 */
	@Bean
	public LockProvider lockProvider(DataSource dataSource) {
		return new JdbcTemplateLockProvider(dataSource);
	}
}
