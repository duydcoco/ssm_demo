package com.ck.config.webconfig;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebConfig implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		//servletContext.addListener(new SessionListener());
		
		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		encodingFilter.addMappingForUrlPatterns(null, true, "/*");
		encodingFilter.setAsyncSupported(true);
		encodingFilter.setInitParameter("encoding", "UTF-8");
		
//		FilterRegistration.Dynamic crosFilter = servletContext.addFilter("crosFilter", com.jdrx.gateway.filter.CrossFilter.class);
//		crosFilter.addMappingForUrlPatterns(null, true, "/*");
//		crosFilter.setAsyncSupported(true);
		
		ServletRegistration.Dynamic springMvc = servletContext.addServlet("springMvc", new DispatcherServlet());
		springMvc.addMapping("/");
		springMvc.setInitParameter("contextClass","org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
		springMvc.setInitParameter("contextConfigLocation","com.ck.config.webconfig.BaseConfig");
		springMvc.setLoadOnStartup(1);
		springMvc.setAsyncSupported(true);
	}
}