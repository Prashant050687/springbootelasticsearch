package com.prashant.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Profile("!test")
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.prashant.elasticsearch.es.repo")
public class EsConfigHighLevelClient extends AbstractElasticsearchConfiguration {
  @Value("${elasticsearch.host}")
  private String EsHost;

  @Value("${elasticsearch.clustername}")
  private String EsClusterName;

  @Override
  @Bean
  public RestHighLevelClient elasticsearchClient() {

    RestHighLevelClient client = new RestHighLevelClient(
      RestClient.builder(HttpHost.create(EsHost)));

    return client;
  }

}
