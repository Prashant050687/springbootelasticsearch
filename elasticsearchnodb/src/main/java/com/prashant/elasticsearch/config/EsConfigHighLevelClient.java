package com.prashant.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Profile("!test")
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.prashant.elasticsearch.es.repo")
public class EsConfigHighLevelClient {
  @Value("${elasticsearch.host}")
  private String EsHost;

  @Value("${elasticsearch.port}")
  private int EsPort;

  @Value("${elasticsearch.clustername}")
  private String EsClusterName;

  @Bean
  public RestClient restClient() {
    return RestClient.builder(new HttpHost(EsHost, EsPort)).build();
  }

}
