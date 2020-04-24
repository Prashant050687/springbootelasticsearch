package com.prashant.elasticsearch.es.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.prashant.elasticsearch.dto.EmployeeDTO;

public interface EmployeeESRepo extends ElasticsearchRepository<EmployeeDTO, Long> {

}
