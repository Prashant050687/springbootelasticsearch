package com.prashant.elasticsearch.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

import com.prashant.elasticsearch.db.repo.ContractTypeRepository;
import com.prashant.elasticsearch.domain.ContractType;
import com.prashant.elasticsearch.domain.Employee;
import com.prashant.elasticsearch.domain.EmployeeType;
import com.prashant.elasticsearch.domain.exception.BusinessException;
import com.prashant.elasticsearch.dto.ContractTypeDTO;
import com.prashant.elasticsearch.dto.EmployeeDTO;
import com.prashant.elasticsearch.filter.dto.ESSearchFilter;
import com.prashant.elasticsearch.service.EmployeeESService;
import com.prashant.elasticsearch.service.EmployeeServiceType;

@RestController
@ExposesResourceFor(EmployeeDTO.class)
@RequestMapping("/employee")
public class EmployeeController implements IEmployeeController {

  @Autowired
  ContractTypeRepository contractTypeRepo;

  @Autowired
  private Collection<EmployeeServiceType<? extends EmployeeDTO, ? extends Employee>> employeeServices;

  @Autowired
  private EmployeeESService employeeESService;

  private EmployeeServiceType<? extends EmployeeDTO, ? extends Employee> getEmployeeService(EmployeeType employeeType) {
    return employeeServices.stream().filter(e -> e.getEmployeeType().equals(employeeType))
      .findFirst()
      .orElseThrow(() -> new BusinessException("Employee Service Issue", "Employee Service Not found", HttpStatus.BAD_REQUEST.value()));

  }

  @Override
  @GetMapping(value = "/{id}")
  public ResponseEntity<? extends EmployeeDTO> getEmployeeById(@PathVariable(name = "id") final Long id) {
    EmployeeDTO employee = getEmployeeService(EmployeeType.STANDARD_EMPLOYEE).getEmployeeById(id);
    if (employee.getEmployeeType().equals(EmployeeType.PROJECT_LEADER)) {
      return ResponseEntity.ok(getEmployeeService(EmployeeType.PROJECT_LEADER).getEmployeeById(id));
    }
    return ResponseEntity.ok(employee);
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
  public ResponseEntity<Page<EmployeeDTO>> searchEmployees(final Pageable pageable, @RequestBody(required = false) ESSearchFilter esSearchFilter) {
    Page<EmployeeDTO> result = null;
    if (esSearchFilter == null) {
      result = employeeESService.findAll(pageable);
    } else {
      result = employeeESService.searchEmployeeByCriteria(esSearchFilter, pageable);
    }
    return ResponseEntity.ok(result);
  }

  @Override
  @GetMapping(value = "/contracTypes")
  public ResponseEntity<List<ContractTypeDTO>> findContractTypes() {
    List<ContractType> contractTypesFromDB = contractTypeRepo.findAll();
    List<ContractTypeDTO> contractTypes = new ArrayList<ContractTypeDTO>();
    for (ContractType contractType : contractTypesFromDB) {
      ContractTypeDTO contractTypeDTO = new ContractTypeDTO(contractType.getId(), contractType.getType());
      contractTypes.add(contractTypeDTO);
    }
    return ResponseEntity.ok(contractTypes);
  }

}
