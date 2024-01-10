package com.example.multiplesource.internal.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Profile({"local"})
@Configuration
@EnableConfigurationProperties(MysqlHibernateProperty.class)
@EnableJpaRepositories(
		basePackages = "com.example.multiplesource.internal",
		entityManagerFactoryRef = "mainEntityManager",
		transactionManagerRef = "mainEntityTransactionManager"
)
public class MainDataSourceConfig {

	private final MysqlHibernateProperty mysqlHibernateProperty;

	public MainDataSourceConfig(MysqlHibernateProperty mysqlHibernateProperty) {
		this.mysqlHibernateProperty = mysqlHibernateProperty;
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.customer.hikari")
	public DataSourceProperties mainDatabaseSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary
	@Bean
	public DataSource mainDataSource() {
		DataSourceProperties dataSourceProperties = mainDatabaseSourceProperties();
		return dataSourceProperties.initializeDataSourceBuilder()
				.type(HikariDataSource.class).build();
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean mainEntityManager() {
		var localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setDataSource(mainDataSource());
		localContainerEntityManagerFactoryBean.setPackagesToScan("com.example.multiplesource.internal");

		var hibernateVendor = new HibernateJpaVendorAdapter();
		hibernateVendor.setShowSql(true);
		hibernateVendor.setGenerateDdl(true);
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateVendor);

		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.ddl-auto", mysqlHibernateProperty.ddlAuto());
		properties.put("hibernate.format_sql", mysqlHibernateProperty.formatSql());
		localContainerEntityManagerFactoryBean.setJpaPropertyMap(properties);

		return localContainerEntityManagerFactoryBean;
	}

	@Bean
	@Primary
	public PlatformTransactionManager mainEntityTransactionManager() {
		var transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(mainEntityManager().getObject());

		return transactionManager;
	}
}
