package com.prashant.elasticsearch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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
import com.prashant.elasticsearch.dto.ContractTypeDTO;
import com.prashant.elasticsearch.dto.EmployeeDTO;
import com.prashant.elasticsearch.dto.EmployeeType;
import com.prashant.elasticsearch.filter.dto.ESFilterCondition;
import com.prashant.elasticsearch.filter.dto.ESSearchFilter;
import com.prashant.elasticsearch.filter.dto.FilterOperation;
import com.prashant.elasticsearch.service.EmployeeESService;

@SpringBootTest
@ActiveProfiles({"test"})
class ElasticsearchApplicationTest {

  private static final String COMPANY_NAME = "Test_1234";

  @Autowired
  ObjectMapper objectMapper;

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

    EmployeeDTO employeeSaved = esService.saveEmployee(employeeDTO);
    employeeSaved.setFirstName("Preshant");
    employeeSaved = esService.saveEmployee(employeeSaved);

    EmployeeDTO employee = esService.findById(employeeSaved.getId());
    assertEquals(employeeSaved.getFirstName(), employee.getFirstName());

    // Search using criteria
    ESSearchFilter esSearchFilter = buildSearchCriteria();

    List<EmployeeDTO> employees = esService.searchEmployeeByCriteria(esSearchFilter);

    assertEquals("Preshant", employees.get(0).getFirstName());

    // search using pagination and criteria
    Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));

    Page<EmployeeDTO> employeePageable = esService.searchEmployeeByCriteria(esSearchFilter, pageable);
    System.out.println(employeePageable.getContent().get(0).getCreatedDate());

  }

  private ESSearchFilter buildSearchCriteria() {
    ESSearchFilter esSearchFilter = new ESSearchFilter();
    List<ESFilterCondition> conditions = new ArrayList<ESFilterCondition>();

    ESFilterCondition filterCondition1 = new ESFilterCondition();
    filterCondition1.setFieldName("employeeType");
    filterCondition1.setValue1("STANDARD_EMPLOYEE");
    filterCondition1.setOperation(FilterOperation.EQ);
    conditions.add(filterCondition1);

    ESFilterCondition filterCondition2 = new ESFilterCondition();
    filterCondition2.setFieldName("firstName");
    filterCondition2.setValue1("Preshant");
    filterCondition2.setOperation(FilterOperation.EQ);
    conditions.add(filterCondition2);

    ESFilterCondition filterCondition4 = new ESFilterCondition();
    filterCondition4.setFieldName("salary");
    filterCondition4.setValue1("2500");
    filterCondition4.setOperation(FilterOperation.LT);
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
