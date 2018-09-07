package com.messages.utilities;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Configuration
@PropertySource("classpath:application.properties")
@Component
public class MysqlDataConfiguration{
	
	@Primary
	@Bean(name="source")
	@ConfigurationProperties(prefix ="spring.datasource")
	public DataSourceProperties DatabaseProperties() {
		return new DataSourceProperties();
	}
	
	@Bean(name="mysqldb")
	@Qualifier("source")
	@ConfigurationProperties("spring.datasource")
	public DataSource sqlDataSource() {
		return DatabaseProperties().initializeDataSourceBuilder().build();
	}

	@Autowired
	@Qualifier("mysqldb")
	@Bean(name ="jdbc")
	public JdbcTemplate customJdbc(DataSource mysqldb){
		return new JdbcTemplate(mysqldb);
	}
}