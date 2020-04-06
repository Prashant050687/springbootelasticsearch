package com.prashant.elasticsearch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashant.elasticsearch.domain.Employee;
import com.prashant.elasticsearch.domain.EmployeeType;
import com.prashant.elasticsearch.domain.ProjectLeader;
import com.prashant.elasticsearch.dto.ContractTypeDTO;
import com.prashant.elasticsearch.dto.EmployeeDTO;
import com.prashant.elasticsearch.dto.EmployeeES;
import com.prashant.elasticsearch.dto.ProjectLeaderDTO;
import com.prashant.elasticsearch.filter.dto.ESFilterCondition;
import com.prashant.elasticsearch.filter.dto.ESSearchFilter;
import com.prashant.elasticsearch.filter.dto.FilterOperation;
import com.prashant.elasticsearch.service.EmployeeESService;
import com.prashant.elasticsearch.service.EmployeeServiceType;
import com.prashant.elasticsearch.service.ProjectLeaderService;
import com.prashant.elasticsearch.service.StandardEmployeeService;

@SpringBootTest
@ActiveProfiles({"test"})
class ElasticsearchApplicationTest {

  private static final String COMPANY_NAME = "Test_1234";

  @Autowired
  StandardEmployeeService standardEmployeeService;

  @Autowired
  ProjectLeaderService projectLeaderService;

  @Autowired
  ObjectMapper objectMapper;

  @SuppressWarnings("rawtypes")
  @Autowired
  private Collection<EmployeeServiceType> employeeServices;

  @Autowired
  private EmployeeESService esService;

  @Test
  public void contextLoads() {
  }

  @Test
  public void testCreateUpdateAndFetchEmployee() {
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setFirstName("Prashant");
    employeeDTO.setLastName("Hariharan");
    employeeDTO.setSalary(1000.00);
    employeeDTO.setContractType(new ContractTypeDTO(1L, "Permanent"));
    employeeDTO.setEmployeeType(EmployeeType.STANDARD_EMPLOYEE);

    EmployeeDTO employeeSaved = standardEmployeeService.saveEmployee(employeeDTO);
    employeeSaved.setFirstName("Preshant");
    employeeSaved = standardEmployeeService.saveEmployee(employeeSaved);

    Employee employee = standardEmployeeService.findEmployeeById(employeeSaved.getId());
    assertEquals(employeeSaved.getFirstName(), employee.getFirstName());

  }

  @Test
  public void testCreateAndFetchProjectLeader() {
    ProjectLeaderDTO projectLeaderDTO = new ProjectLeaderDTO();
    projectLeaderDTO.setFirstName("Cristiano");
    projectLeaderDTO.setLastName("Ronaldo");
    projectLeaderDTO.setSalary(2200.00);
    projectLeaderDTO.setContractType(new ContractTypeDTO(1L, "Permanent"));
    projectLeaderDTO.setEmployeeType(EmployeeType.PROJECT_LEADER);

    projectLeaderDTO.setProjectName(COMPANY_NAME);
    projectLeaderDTO.setReportingEmployees(100);

    ProjectLeaderDTO savedPL = projectLeaderService.saveEmployee(projectLeaderDTO);

    ProjectLeader employee = projectLeaderService.findEmployeeById(savedPL.getId());
    assertEquals(COMPANY_NAME, employee.getProjectName());
    assertEquals("Cristiano", employee.getFirstName());

  }

  @Test
  @SuppressWarnings("unchecked")
  public void testCreateProjectLeaderPolymorphicAndSearch() {
    ProjectLeaderDTO projectLeaderDTO = new ProjectLeaderDTO();
    projectLeaderDTO.setFirstName("Project");
    projectLeaderDTO.setLastName("Leader");
    projectLeaderDTO.setSalary(3000.11);
    projectLeaderDTO.setContractType(new ContractTypeDTO(1L, "Permanent"));
    projectLeaderDTO.setEmployeeType(EmployeeType.PROJECT_LEADER);

    projectLeaderDTO.setProjectName(COMPANY_NAME);
    projectLeaderDTO.setReportingEmployees(100);

    EmployeeServiceType<ProjectLeaderDTO, ProjectLeader> polyEmpService = employeeServices.stream().filter(e -> e.getEmployeeType().equals(EmployeeType.PROJECT_LEADER))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Employee Service Not found"));

    try {
      System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(projectLeaderDTO));
    } catch (Exception e) {
      e.printStackTrace();
    }

    ProjectLeaderDTO savedPL = polyEmpService.saveEmployee(projectLeaderDTO);

    ProjectLeaderDTO employee = polyEmpService.getEmployeeById(savedPL.getId());
    assertEquals(COMPANY_NAME, employee.getProjectName());
    assertEquals("Project", employee.getFirstName());

    // Search using criteria
    ESSearchFilter esSearchFilter = buildSearchCriteria();

    List<EmployeeES> employees = esService.searchEmployeeByCriteria(esSearchFilter);

    assertEquals("Project", employees.get(0).getFirstName());
    System.out.println(employees.get(0).getCreatedDate());

    // search using pagination and criteria
    Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));

    Page<EmployeeES> employeePageable = esService.searchEmployeeByCriteria(esSearchFilter, pageable);
    System.out.println(employeePageable.getContent().get(0).getCreatedDate());

  }

  private ESSearchFilter buildSearchCriteria() {
    ESSearchFilter esSearchFilter = new ESSearchFilter();
    List<ESFilterCondition> conditions = new ArrayList<ESFilterCondition>();

    ESFilterCondition filterCondition1 = new ESFilterCondition();
    filterCondition1.setFieldName("employeeType");
    filterCondition1.setValue1("PROJECT_LEADER");
    filterCondition1.setOperation(FilterOperation.EQ);
    conditions.add(filterCondition1);

    ESFilterCondition filterCondition2 = new ESFilterCondition();
    filterCondition2.setFieldName("firstName");
    filterCondition2.setValue1("Cristiano");
    filterCondition2.setOperation(FilterOperation.EQ);
    conditions.add(filterCondition2);

    ESFilterCondition filterCondition3 = new ESFilterCondition();
    filterCondition3.setFieldName("firstName");
    filterCondition3.setValue1("Project");
    filterCondition3.setOperation(FilterOperation.EQ);
    conditions.add(filterCondition3);

    ESFilterCondition filterCondition4 = new ESFilterCondition();
    filterCondition4.setFieldName("salary");
    filterCondition4.setValue1("2500");
    filterCondition4.setOperation(FilterOperation.GT);
    conditions.add(filterCondition4);

    esSearchFilter.setConditions(conditions);
    try {
      System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(esSearchFilter));
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return esSearchFilter;
  }

}
