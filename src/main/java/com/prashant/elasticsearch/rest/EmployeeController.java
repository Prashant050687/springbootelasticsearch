package com.prashant.elasticsearch.rest;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prashant.elasticsearch.domain.Employee;
import com.prashant.elasticsearch.domain.EmployeeType;
import com.prashant.elasticsearch.domain.exception.BusinessException;
import com.prashant.elasticsearch.dto.EmployeeDTO;
import com.prashant.elasticsearch.dto.EmployeeES;
import com.prashant.elasticsearch.filter.dto.ESSearchFilter;
import com.prashant.elasticsearch.service.EmployeeESService;
import com.prashant.elasticsearch.service.EmployeeServiceType;

@RestController
@ExposesResourceFor(EmployeeDTO.class)
@RequestMapping("/employee")
public class EmployeeController implements IEmployeeController {

  @Autowired
  private Collection<EmployeeServiceType<? extends EmployeeDTO, ? extends Employee>> employeeServices;

  @Autowired
  private EmployeeESService employeeESService;

  @Override
  @GetMapping(value = "")
  public ResponseEntity<List<? extends EmployeeDTO>> findAllEmployees(final Pageable pageable,
    @RequestParam(name = "employeeType", defaultValue = "STANDARD_EMPLOYEE") final EmployeeType employeeType) {
    return ResponseEntity.ok(getEmployeeService(employeeType).findAllEmployees());
  }

  private EmployeeServiceType<? extends EmployeeDTO, ? extends Employee> getEmployeeService(EmployeeType employeeType) {
    return employeeServices.stream().filter(e -> e.getEmployeeType().equals(employeeType))
      .findFirst()
      .orElseThrow(() -> new BusinessException("Employee Service Issue", "Employee Service Not found", HttpStatus.BAD_REQUEST.value()));

  }

  @Override
  @GetMapping(value = "/{id}")
  public ResponseEntity<? extends EmployeeDTO> getEmployeeById(@PathVariable(name = "id") final Long id,
    @RequestParam(name = "employeeType", defaultValue = "STANDARD_EMPLOYEE") final EmployeeType employeeType) {
    return ResponseEntity.ok(getEmployeeService(employeeType).getEmployeeById(id));
  }

  @Override
  @PostMapping(value = "")
  public ResponseEntity<? extends EmployeeDTO> saveEmployee(@RequestBody final String json,
    @RequestParam(name = "employeeType", defaultValue = "STANDARD_EMPLOYEE") final EmployeeType employeeType) {

    return ResponseEntity.ok(getEmployeeService(employeeType).saveEmployee(json));
  }

  @Override
  @DeleteMapping(value = "/{id}")
  public HttpEntity<Void> deleteEmployeeById(@PathVariable(name = "id") Long id, EmployeeType employeeType) {
    getEmployeeService(employeeType).deleteEmployee(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Override
  @PostMapping(value = "/search")
  public ResponseEntity<Page<EmployeeES>> searchEmployees(final Pageable pageable, @RequestBody @Valid ESSearchFilter esSearchFilter) {
    return ResponseEntity.ok(employeeESService.searchEmployeeByCriteria(esSearchFilter, pageable));
  }

  @Override
  @PostMapping(value = "/searchall")
  public ResponseEntity<List<EmployeeES>> searchEmployees(@RequestBody @Valid ESSearchFilter esSearchFilter) {
    return ResponseEntity.ok(employeeESService.searchEmployeeByCriteria(esSearchFilter));
  }

}