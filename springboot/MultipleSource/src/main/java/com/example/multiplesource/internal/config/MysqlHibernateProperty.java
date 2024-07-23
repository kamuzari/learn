package com.example.multiplesource.internal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.jpa")
public record MysqlHibernateProperty(
		String ddlAuto,
		String formatSql) {

}
