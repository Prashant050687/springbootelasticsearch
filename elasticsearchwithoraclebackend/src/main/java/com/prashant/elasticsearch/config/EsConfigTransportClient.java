package com.prashant.elasticsearch.config;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.geo.CustomGeoModule;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mapping.MappingException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Profile("test")
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.prashant.elasticsearch.es.repo")
public class EsConfigTransportClient {
  @Value("${elasticsearch.host}")
  private String EsHost;

  @Value("${elasticsearch.port}")
  private int EsPort;

  @Value("${elasticsearch.clustername}")
  private String EsClusterName;

  @Bean
  public Client client() throws Exception {

    Settings esSettings = Settings.builder()
      .put("cluster.name", EsClusterName)
      .build();

    TransportClient client = new PreBuiltTransportClient(esSettings);
    client.addTransportAddress(new TransportAddress(InetAddress.getByName(EsHost), EsPort));
    return client;

  }

  @Bean
  public ElasticsearchOperations elasticsearchTemplate() throws Exception {
    return new ElasticsearchTemplate(client(), new CustomEntityMapper());
  }

  public class CustomEntityMapper implements EntityMapper {

    private final ObjectMapper objectMapper;

    public CustomEntityMapper() {
      objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
      objectMapper.registerModule(new CustomGeoModule());
      objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public String mapToString(Object object) throws IOException {
      return objectMapper.writeValueAsString(object);
    }

    @Override
    public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
      return objectMapper.readValue(source, clazz);
    }

    @Override
    public Map<String, Object> mapObject(Object source) {

      try {
        return objectMapper.readValue(mapToString(source), HashMap.class);
      } catch (IOException e) {
        throw new MappingException(e.getMessage(), e);
      }
    }

    @Override
    public <T> T readObject(Map<String, Object> source, Class<T> targetType) {

      try {
        return mapToObject(mapToString(source), targetType);
      } catch (IOException e) {
        throw new MappingException(e.getMessage(), e);
      }
    }
  }
}

// Embedded Elasticsearch Server
/*
 * @Bean
 * public ElasticsearchOperations elasticsearchTemplate() {
 * return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
 * }
 */
