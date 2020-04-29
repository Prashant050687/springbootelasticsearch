package com.prashant.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.prashant.elasticsearch"})
@EntityScan(basePackages = {"com.prashant.elasticsearch.domain", "org.springframework.data.jpa.convert.threeten"})
@EnableJpaRepositories(basePackages = {"com.prashant.elasticsearch.db.repo"})
public class ElasticsearchApplication {

  public static void main(String[] args) {
    SpringApplication.run(ElasticsearchApplication.class, args);
  }

}
