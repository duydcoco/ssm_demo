package com.ck.config.serviceconfig;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.ck.**.dao")
@EnableTransactionManagement
public class MybatisConfig {

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(
			@Autowired DataSource dataSource,
			@Autowired ResourcePatternResolver resourcePatternResolver
			/**@Autowired PagePlugin pagePlugin**/)throws Exception {
		
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setCallSettersOnNulls(true);// 返回resultType=”map”时，如果数据为空的字段，则该字段省略不显示，这里配置为true显示
		configuration.setMapUnderscoreToCamelCase(true);
		
		sqlSessionFactoryBean.setConfiguration(configuration);
		sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath*:mappings/**/*Mapper.xml"));
//		Interceptor[] interceptors = {pagePlugin};
//		sqlSessionFactoryBean.setPlugins(interceptors);
		return sqlSessionFactoryBean;
	}

	@Bean
	public PlatformTransactionManager transactionManager(@Autowired DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}