package com.prashant.elasticsearch.es.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.prashant.elasticsearch.dto.EmployeeES;

public interface EmployeeESRepo extends ElasticsearchRepository<EmployeeES, Long> {

}
