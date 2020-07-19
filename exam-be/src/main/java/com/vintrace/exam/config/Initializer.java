package com.vintrace.exam.config;

import org.springframework.boot.web.servlet.ServletContextInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class Initializer implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
    }
}
