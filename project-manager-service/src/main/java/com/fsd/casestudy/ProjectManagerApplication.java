package com.fsd.casestudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.fsd.casestudy.config.CorsFilter;

/**
 * This class acts as application startup for starting Project Manager
 * application as Spring boot application
 * 
 * @author 463657
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
public class ProjectManagerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagerApplication.class, args);
	}

	/**
	 * CORS Filter for allowing endpoints
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean corsFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();

		registrationBean.setFilter(new CorsFilter());
		registrationBean.addUrlPatterns("/*");

		return registrationBean;
	}

}
