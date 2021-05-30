package com.example.springbootcore.autoconfigure;

import com.example.springbootcore.config.WebConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnWebApplication
@Import(WebConfiguration.class)
public class WebAutoConfiguration {
}
