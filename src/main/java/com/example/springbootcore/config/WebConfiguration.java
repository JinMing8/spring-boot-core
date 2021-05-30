package com.example.springbootcore.config;

import com.example.springbootcore.domain.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
public class WebConfiguration {

    private final static Logger log = LoggerFactory.getLogger(WebConfiguration.class);

    @Bean
    public ApplicationRunner applicationRunner(BeanFactory beanFactory) {
        return args -> {
            log.info("HelloController :" + beanFactory.getBean("helloController").getClass().getName());
            log.info("WebConfiguration :" + beanFactory.getBean("webConfiguration").getClass().getName());
            log.info("ApplicationRunner :" + beanFactory.getBean("applicationRunner").getClass().getName());
            log.info("Client :" + beanFactory.getBean("client").getClass().getName());
        };
    }

    @Bean
    public Client client() {
        return new Client();
    }
}
