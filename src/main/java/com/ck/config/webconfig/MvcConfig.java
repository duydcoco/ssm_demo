package com.ck.config.webconfig;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/page/**").addResourceLocations(
				"/WEB-INF/page/");
		registry.addResourceHandler("/static/**").addResourceLocations(
				"/static/");
		registry.addResourceHandler("/upload/**").addResourceLocations(
				"/upload/");
	}

//	public void addInterceptors(
//			org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
//		String[] excludePathPatterns = { "/", "/static/**", "/page/**","/login/logout" };
//		LoginInterceptor logoutInterceptor = new LoginInterceptor();
//		registry.addInterceptor(logoutInterceptor).addPathPatterns("/**")
//				.excludePathPatterns(excludePathPatterns);
//	};

//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**")
//			.allowedOrigins("http://localhost:8989")
//			.allowedHeaders("X-Requested-With","Content-Type")
//			.allowedMethods("*")
//			.allowCredentials(true);
//		super.addCorsMappings(registry);
//	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("utf-8");
		multipartResolver.setMaxUploadSize(1048576000);
		multipartResolver.setMaxInMemorySize(40960);
		return multipartResolver;
	}

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(stringHttpMessageConverter());
		converters.add(jsonHttpMessageConverter());
		converters.add(new ByteArrayHttpMessageConverter());
	}

	private HttpMessageConverter<?> jsonHttpMessageConverter() {
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_JSON);
		fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
		fastJsonHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
				SerializerFeature.WriteBigDecimalAsPlain,
				SerializerFeature.WriteDateUseDateFormat,
				SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullNumberAsZero);
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		return fastJsonHttpMessageConverter;
	}

	private HttpMessageConverter<?> stringHttpMessageConverter() {
		StringHttpMessageConverter utf8StringHttpMessageConverter = new StringHttpMessageConverter();
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.TEXT_HTML);
		utf8StringHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
		utf8StringHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
		return utf8StringHttpMessageConverter;
	}
}