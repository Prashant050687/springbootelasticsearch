package com.prashant.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.prashant.elasticsearch"})
@EntityScan(basePackages = {"com.prashant.elasticsearch.domain", "org.springframework.data.jpa.convert.threeten"})
public class ElasticsearchApplication {

  public static void main(String[] args) {
    SpringApplication.run(ElasticsearchApplication.class, args);
  }

}
