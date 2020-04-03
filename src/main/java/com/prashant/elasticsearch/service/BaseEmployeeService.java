package com.prashant.elasticsearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashant.elasticsearch.dto.EmployeeDTO;
import com.prashant.elasticsearch.dto.EmployeeES;

import lombok.Data;

@Service
@Data
public class BaseEmployeeService {
  @Autowired
  private ObjectMapper mapper;

  @Autowired
  EmployeeESService employeeESService;

  private EmployeeES convertEmployeeToESDTO(EmployeeDTO employeeDTO) {
    EmployeeES employee = new EmployeeES();
    employee.setId(employeeDTO.getId());
    employee.setFirstName(employeeDTO.getFirstName());
    employee.setLastName(employeeDTO.getLastName());
    employee.setSalary(employeeDTO.getSalary());
    employee.setContractType(employeeDTO.getContractType());
    employee.setEmployeeType(employeeDTO.getEmployeeType());
    employee.setCreatedDate(employeeDTO.getCreatedDate());
    return employee;
  }

  protected void addToElasticSearch(EmployeeDTO employeeDTO) {
    employeeESService.saveEmployee(convertEmployeeToESDTO(employeeDTO));
  }
}
