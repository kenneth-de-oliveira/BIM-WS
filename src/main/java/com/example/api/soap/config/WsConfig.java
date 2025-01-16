package com.example.api.soap.config;

import com.example.shared.util.LoggerHandler;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import java.util.List;

@EnableWs
@Configuration
public class WsConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> servletConfiguration(ApplicationContext applicationContext) {
        var servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/BIM-WS/*");
    }

    @Bean
    public LoggerHandler loggerHandler() {
        return new LoggerHandler();
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(loggerHandler());
    }

}