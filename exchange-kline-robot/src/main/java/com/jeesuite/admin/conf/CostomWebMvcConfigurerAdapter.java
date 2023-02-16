package com.jeesuite.admin.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jeesuite.admin.interceptor.SecurityInterceptor;

@Configuration
public class CostomWebMvcConfigurerAdapter implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/**").excludePathPatterns("/login.html","/auth/**","/webjars/**");
	}

}
