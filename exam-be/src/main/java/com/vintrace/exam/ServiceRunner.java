package com.vintrace.exam;

import com.vintrace.exam.config.Initializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.faces.webapp.FacesServlet;

@SpringBootApplication
public class ServiceRunner extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRunner.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(new Class[] { ServiceRunner.class, Initializer.class});
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		FacesServlet servlet = new FacesServlet();
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.jsf");
		return servletRegistrationBean;
	}

	@Bean
	public com.sun.faces.config.ConfigureListener mojarraConfigureListener() {
		return new com.sun.faces.config.ConfigureListener();
	}

}
