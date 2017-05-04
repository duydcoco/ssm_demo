package com.ck.config.webconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan(value="com.ck",includeFilters={@Filter({RestController.class})})
@PropertySource("classpath:app.properties")
public class BaseConfig {
	@Bean
	public PropertySourcesPlaceholderConfigurer propertiesReader() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}