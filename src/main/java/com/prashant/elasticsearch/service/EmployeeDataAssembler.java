package com.prashant.elasticsearch.service;

import org.springframework.stereotype.Component;

import com.prashant.elasticsearch.domain.ContractType;
import com.prashant.elasticsearch.domain.Employee;
import com.prashant.elasticsearch.dto.ContractTypeDTO;
import com.prashant.elasticsearch.dto.EmployeeDTO;

@Component
public class EmployeeDataAssembler {
  public Employee convertToEmployee(EmployeeDTO employeeDTO) {
    Employee employee = new Employee();
    employee.setId(employeeDTO.getId());
    employee.setFirstName(employeeDTO.getFirstName());
    employee.setLastName(employeeDTO.getLastName());
    employee.setSalary(employeeDTO.getSalary());
    employee.setContractType(new ContractType(employeeDTO.getContractType().getId(), employeeDTO.getContractType().getType()));
    employee.setEmployeeType(employeeDTO.getEmployeeType());
    return employee;
  }

  public EmployeeDTO convertToEmployeeDTO(Employee employee) {
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setId(employee.getId());
    employeeDTO.setFirstName(employee.getFirstName());
    employeeDTO.setLastName(employee.getLastName());
    employeeDTO.setSalary(employee.getSalary());
    employeeDTO.setContractType(new ContractTypeDTO(employee.getContractType().getId(), employee.getContractType().getType()));
    employeeDTO.setEmployeeType(employee.getEmployeeType());
    return employeeDTO;
  }
}
