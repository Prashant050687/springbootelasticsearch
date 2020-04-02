package com.prashant.elasticsearch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.prashant.elasticsearch.domain.Employee;
import com.prashant.elasticsearch.domain.EmployeeType;
import com.prashant.elasticsearch.domain.ProjectLeader;
import com.prashant.elasticsearch.dto.ContractTypeDTO;
import com.prashant.elasticsearch.dto.EmployeeDTO;
import com.prashant.elasticsearch.dto.EmployeeES;
import com.prashant.elasticsearch.dto.ProjectLeaderDTO;
import com.prashant.elasticsearch.service.EmployeeESService;
import com.prashant.elasticsearch.service.EmployeeServiceType;
import com.prashant.elasticsearch.service.ProjectLeaderService;
import com.prashant.elasticsearch.service.StandardEmployeeService;

@SpringBootTest
@ActiveProfiles({"test"})
class ElasticsearchApplicationTest {

  @Autowired
  StandardEmployeeService standardEmployeeService;

  @Autowired
  ProjectLeaderService projectLeaderService;

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
    employeeDTO.setSalary(1111.11);
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
    projectLeaderDTO.setSalary(1111.11);
    projectLeaderDTO.setContractType(new ContractTypeDTO(1L, "Permanent"));
    projectLeaderDTO.setEmployeeType(EmployeeType.PROJECT_LEADER);

    projectLeaderDTO.setProjectName("Adidas");
    projectLeaderDTO.setReportingEmployees(100);

    ProjectLeaderDTO savedPL = projectLeaderService.saveEmployee(projectLeaderDTO);

    ProjectLeader employee = projectLeaderService.findEmployeeById(savedPL.getId());
    assertEquals("Adidas", employee.getProjectName());
    assertEquals("Cristiano", employee.getFirstName());

  }

  @Test
  @SuppressWarnings("unchecked")
  public void testCreateProjectLeaderPolymorphic() {
    ProjectLeaderDTO projectLeaderDTO = new ProjectLeaderDTO();
    projectLeaderDTO.setFirstName("Project");
    projectLeaderDTO.setLastName("Leader");
    projectLeaderDTO.setSalary(1111.11);
    projectLeaderDTO.setContractType(new ContractTypeDTO(1L, "Permanent"));
    projectLeaderDTO.setEmployeeType(EmployeeType.PROJECT_LEADER);

    projectLeaderDTO.setProjectName("Adidas");
    projectLeaderDTO.setReportingEmployees(100);

    EmployeeServiceType<ProjectLeaderDTO, ProjectLeader> polyEmpService = employeeServices.stream().filter(e -> e.getEmployeeType().equals(EmployeeType.PROJECT_LEADER))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Employee Service Not found"));

    ProjectLeaderDTO savedPL = polyEmpService.saveEmployee(projectLeaderDTO);

    ProjectLeader employee = polyEmpService.findEmployeeById(savedPL.getId());
    assertEquals("Adidas", employee.getProjectName());
    assertEquals("Project", employee.getFirstName());

    List<EmployeeES> employees = esService.searchMultiField("Project", "Leader");
    assertEquals("Project", employees.get(0).getFirstName());

  }

}
