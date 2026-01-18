package com.example.demo.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class H2ConsoleConfiguration {

    @Bean
    public ServletContextInitializer h2ConsoleServletInitializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                var h2Servlet = new JakartaWebServlet();
                var registration = servletContext.addServlet("h2Console", h2Servlet);
                registration.addMapping("/h2-console/*");
                registration.setLoadOnStartup(1);
            }
        };
    }
}
