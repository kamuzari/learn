package com.example.multiplesource.internal.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@EnableJpaRepositories(
		basePackages = "com.example.multiplesource.exterior",
		entityManagerFactoryRef = "exteriorEntityManager",
		transactionManagerRef = "exteriorEntityTransactionManager"
)
public class ExteriorDataSourceConfig {

	private final MysqlHibernateProperty mysqlHibernateProperty;

	public ExteriorDataSourceConfig(MysqlHibernateProperty mysqlHibernateProperty) {
		this.mysqlHibernateProperty = mysqlHibernateProperty;
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.enterprise.hikari")
	public DataSourceProperties exteriorDatabaseSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource exteriorDataSource() {
		return exteriorDatabaseSourceProperties().initializeDataSourceBuilder()
				.type(HikariDataSource.class).build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean exteriorEntityManager() {
		var localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setDataSource(exteriorDataSource());
		localContainerEntityManagerFactoryBean.setPackagesToScan("com.example.multiplesource.exterior");

		var hibernateVendor = new HibernateJpaVendorAdapter();
		hibernateVendor.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
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
	public PlatformTransactionManager exteriorEntityTransactionManager() {
		var transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(exteriorEntityManager().getObject());

		return transactionManager;
	}
}
