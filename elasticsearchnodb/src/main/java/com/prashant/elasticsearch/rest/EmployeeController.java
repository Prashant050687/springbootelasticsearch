package com.prashant.elasticsearch.rest;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import com.prashant.elasticsearch.dto.ContractTypeDTO;
import com.prashant.elasticsearch.dto.EmployeeDTO;
import com.prashant.elasticsearch.dto.EmployeeType;
import com.prashant.elasticsearch.filter.dto.ESSearchFilter;
import com.prashant.elasticsearch.service.EmployeeESService;

@RestController
@ExposesResourceFor(EmployeeDTO.class)
@RequestMapping("/employee")
public class EmployeeController implements IEmployeeController {

  @Autowired
  private EmployeeESService employeeESService;

  @Override
  @GetMapping(value = "/{id}")
  public ResponseEntity<? extends EmployeeDTO> getEmployeeById(@PathVariable(name = "id") final String id) {
    EmployeeDTO employee = employeeESService.findById(id);

    return ResponseEntity.ok(employee);
  }

  @Override
  @PostMapping(value = "")
  public ResponseEntity<? extends EmployeeDTO> saveEmployee(@RequestBody final EmployeeDTO employee) {

    return ResponseEntity.ok(employeeESService.saveEmployee(employee));
  }

  @Override
  @DeleteMapping(value = "/{id}")
  public HttpEntity<Void> deleteEmployeeById(@PathVariable(name = "id") String id, EmployeeType employeeType) {
    employeeESService.deleteEmployee(id);
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

    List<ContractTypeDTO> contractTypes = new ArrayList<ContractTypeDTO>();

    ContractTypeDTO contractTypePermanent = new ContractTypeDTO();
    contractTypePermanent.setId(1L);
    contractTypePermanent.setType("Permanent");

    ContractTypeDTO contractTypeContractual = new ContractTypeDTO();
    contractTypeContractual.setId(2L);
    contractTypeContractual.setType("Contractual");

    ContractTypeDTO contractTypeIntern = new ContractTypeDTO();
    contractTypeIntern.setId(3L);
    contractTypeIntern.setType("Intern");

    ContractTypeDTO contractTypePartTime = new ContractTypeDTO();
    contractTypePartTime.setId(4L);
    contractTypePartTime.setType("Part-Time");

    contractTypes.add(contractTypePermanent);
    contractTypes.add(contractTypeContractual);
    contractTypes.add(contractTypeIntern);
    contractTypes.add(contractTypePartTime);

    return ResponseEntity.ok(contractTypes);
  }

  @Override
  @PostMapping(value = "/multisearch")
  public ResponseEntity<Page<EmployeeDTO>> searchEmployeesMultiMatch(Pageable pageable, String searchString) {
    return ResponseEntity.ok(employeeESService.getMultiMatchSearch(searchString, pageable));
  }

}
