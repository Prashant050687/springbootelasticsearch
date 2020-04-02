package com.prashant.elasticsearch.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@Profile("!test")
@EnableElasticsearchRepositories(basePackages = "com.prashant.elasticsearch.es.repo")
public class EsConfig2 {
  @Bean
  RestHighLevelClient client() {

    ClientConfiguration clientConfiguration = ClientConfiguration.builder()
      .connectedTo("localhost:9200", "localhost:9201")
      .build();

    return RestClients.create(clientConfiguration).rest();
  }

}
