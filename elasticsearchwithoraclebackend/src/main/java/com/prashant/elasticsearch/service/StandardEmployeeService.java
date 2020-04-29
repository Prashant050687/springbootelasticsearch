package com.prashant.elasticsearch.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prashant.elasticsearch.db.repo.EmployeeRepository;
import com.prashant.elasticsearch.domain.Employee;
import com.prashant.elasticsearch.domain.EmployeeType;
import com.prashant.elasticsearch.domain.exception.InvalidResourceException;
import com.prashant.elasticsearch.domain.exception.ResourceNotFoundException;
import com.prashant.elasticsearch.dto.EmployeeDTO;
import com.prashant.elasticsearch.validator.EmployeeValidator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class StandardEmployeeService extends BaseEmployeeService implements EmployeeServiceType<EmployeeDTO, Employee> {

  private final EmployeeRepository employeeRepository;
  private final EmployeeValidator employeeValidator;
  private final EmployeeDataAssembler employeeDataAssembler;

  @Autowired
  public StandardEmployeeService(EmployeeRepository employeeRepository, EmployeeValidator employeeValidator, EmployeeDataAssembler employeeDataAssembler) {
    this.employeeRepository = employeeRepository;
    this.employeeValidator = employeeValidator;
    this.employeeDataAssembler = employeeDataAssembler;
  }

  @Transactional(readOnly = true)
  public Employee findEmployeeById(Long id) {
    Validate.notNull(id, "Employee id cannot be null");
    return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", id));
  }

  @Override
  @Transactional
  public EmployeeDTO saveEmployee(String json) {
    EmployeeDTO employeeDTO = getEmployeeDTO(json);
    return saveEmployee(employeeDTO);

  }

  @Override
  @Transactional
  public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
    List<String> errors = employeeValidator.validateEmployee(employeeDTO);
    if (!CollectionUtils.isEmpty(errors)) {
      throw new InvalidResourceException(errors);
    }
    Employee employee = employeeDataAssembler.convertToEmployee(employeeDTO);
    Employee employeeFromDB = null;

    if (employee.getId() != null) {

      employeeFromDB = findEmployeeById(employeeDTO.getId());
      BeanUtils.copyProperties(employee, employeeFromDB, "id");
      employeeFromDB = employeeRepository.save(employeeFromDB);
    } else {
      employeeFromDB = employeeRepository.save(employee);
    }
    employeeDTO = employeeDataAssembler.convertToEmployeeDTO(employeeFromDB);
    addToElasticSearchIndex(employeeDTO);

    return employeeDTO;
  }

  @Override
  public EmployeeType getEmployeeType() {
    return EmployeeType.STANDARD_EMPLOYEE;
  }

  @Override
  public EmployeeDTO getEmployeeDTO(String json) {
    try {
      return this.getMapper().readValue(json, EmployeeDTO.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<EmployeeDTO> findAllEmployeesByIds(List<Long> ids) {
    List<Employee> employeesFromDB = employeeRepository.findAllById(ids);
    List<EmployeeDTO> employees = new ArrayList<>();
    employeesFromDB.forEach(p -> employees.add(employeeDataAssembler.convertToEmployeeDTO(p)));
    return employees;
  }

  @Override
  @Transactional
  public void deleteEmployee(Long id) {
    Employee employee = findEmployeeById(id);
    employeeRepository.delete(employee);
    deleteFromElasticSearchIndex(id);

  }

  @Override
  @Transactional(readOnly = true)
  public List<EmployeeDTO> findAllEmployees() {
    List<Employee> employeesFromDB = employeeRepository.findAll();
    List<EmployeeDTO> employees = new ArrayList<>();
    employeesFromDB.forEach(p -> employees.add(employeeDataAssembler.convertToEmployeeDTO(p)));
    return employees;
  }

  @Override
  @Transactional(readOnly = true)
  public EmployeeDTO getEmployeeById(Long id) {
    Employee employee = findEmployeeById(id);

    return employeeDataAssembler.convertToEmployeeDTO(employee);
  }

  @Override
  public Page<Employee> findAllEmployeesPageable(Pageable pageable) {
    return employeeRepository.findAll(pageable);
  }

}
