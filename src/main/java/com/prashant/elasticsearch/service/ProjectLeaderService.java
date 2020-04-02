package com.prashant.elasticsearch.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prashant.elasticsearch.db.repo.ProjectLeadRepository;
import com.prashant.elasticsearch.domain.Employee;
import com.prashant.elasticsearch.domain.EmployeeType;
import com.prashant.elasticsearch.domain.ProjectLeader;
import com.prashant.elasticsearch.domain.exception.InvalidResourceException;
import com.prashant.elasticsearch.domain.exception.ResourceNotFoundException;
import com.prashant.elasticsearch.dto.EmployeeDTO;
import com.prashant.elasticsearch.dto.ProjectLeaderDTO;
import com.prashant.elasticsearch.validator.ProjectLeaderValidator;

@Service
public class ProjectLeaderService extends BaseEmployeeService implements EmployeeServiceType<ProjectLeaderDTO, ProjectLeader> {
  private final ProjectLeadRepository projectLeadRepo;

  private final StandardEmployeeService employeeService;

  private final ProjectLeaderValidator projectLeaderValidator;

  public ProjectLeaderService(ProjectLeadRepository projectLeadRepo, StandardEmployeeService employeeService, ProjectLeaderValidator projectLeaderValidator) {
    this.projectLeadRepo = projectLeadRepo;
    this.employeeService = employeeService;
    this.projectLeaderValidator = projectLeaderValidator;
  }

  @Override
  @Transactional(readOnly = true)
  public ProjectLeader findEmployeeById(Long id) {
    Validate.notNull(id, "Project Leader id cannot be null");
    return projectLeadRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project Leader", id));
  }

  @Override
  @Transactional
  public ProjectLeaderDTO saveEmployee(ProjectLeaderDTO projectLeaderDTO) {
    List<String> errors = projectLeaderValidator.validateProjectLeader(projectLeaderDTO);
    if (!CollectionUtils.isEmpty(errors)) {
      throw new InvalidResourceException(errors);
    }
    ProjectLeader projectLeader = convertToProjectLeaderEntity(projectLeaderDTO);
    ProjectLeader projectLeaderFromDB = null;
    if (projectLeader.getId() != null) {
      projectLeaderFromDB = findEmployeeById(projectLeader.getId());
      BeanUtils.copyProperties(projectLeader, projectLeaderFromDB, "id");
      projectLeaderFromDB = projectLeadRepo.save(projectLeaderFromDB);
    } else {
      projectLeaderFromDB = projectLeadRepo.save(projectLeader);
    }

    ProjectLeaderDTO savedProjectLeaderDTO = convertToProjectLeaderDTO(projectLeaderFromDB);

    addToElasticSearch(savedProjectLeaderDTO);

    return savedProjectLeaderDTO;

  }

  private ProjectLeaderDTO convertToProjectLeaderDTO(ProjectLeader projectLeader) {
    ProjectLeaderDTO savedProjectLeaderDTO = new ProjectLeaderDTO();
    EmployeeDTO savedEmployeeDTO = employeeService.getEmployeeDataAssembler().convertToEmployeeDTO(projectLeader);
    savedProjectLeaderDTO.setProjectName(projectLeader.getProjectName());
    savedProjectLeaderDTO.setReportingEmployees(projectLeader.getReportingEmployees());
    BeanUtils.copyProperties(savedEmployeeDTO, savedProjectLeaderDTO);
    return savedProjectLeaderDTO;
  }

  private ProjectLeader convertToProjectLeaderEntity(ProjectLeaderDTO projectLeaderDTO) {
    ProjectLeader projectLeader = new ProjectLeader();
    // copy employee data
    Employee employee = employeeService.getEmployeeDataAssembler().convertToEmployee(projectLeaderDTO);
    projectLeader.setProjectName(projectLeaderDTO.getProjectName());
    projectLeader.setReportingEmployees(projectLeaderDTO.getReportingEmployees());
    BeanUtils.copyProperties(employee, projectLeader);
    return projectLeader;
  }

  @Override
  public EmployeeType getEmployeeType() {
    return EmployeeType.PROJECT_LEADER;
  }

  @Override
  public ProjectLeaderDTO getEmployeeDTO(String json) {
    try {
      return this.getMapper().readValue(json, ProjectLeaderDTO.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public List<ProjectLeaderDTO> findAllEmployeesByIds(List<Long> ids) {
    List<ProjectLeader> projectLeadersFromDB = projectLeadRepo.findAllById(ids);
    List<ProjectLeaderDTO> projectLeaders = new ArrayList<ProjectLeaderDTO>();
    projectLeadersFromDB.forEach(p -> projectLeaders.add(convertToProjectLeaderDTO(p)));
    return projectLeaders;
  }

  @Override
  public void deleteEmployee(Long id) {
    ProjectLeader projectLeader = findEmployeeById(id);
    projectLeadRepo.delete(projectLeader);

  }
}
