package com.prashant.elasticsearch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry
      .addMapping("/**")
      .allowedMethods("HEAD", "OPTIONS", "GET", "PUT", "POST", "DELETE", "PATCH")
      .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization")
      .allowedOrigins("*");
  }
}
