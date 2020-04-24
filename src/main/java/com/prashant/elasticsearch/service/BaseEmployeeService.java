package com.prashant.elasticsearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashant.elasticsearch.dto.EmployeeDTO;

import lombok.Getter;

@Service
@Getter
public class BaseEmployeeService {
  @Autowired
  private ObjectMapper mapper;

  @Autowired
  EmployeeESService employeeESService;

  protected void addToElasticSearchIndex(EmployeeDTO employeeDTO) {
    employeeESService.saveEmployee(employeeDTO);
  }

  protected void deleteFromElasticSearchIndex(Long id) {
    employeeESService.deleteEmployee(id);
  }
}
