package com.prashant.elasticsearch.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prashant.elasticsearch.dto.ProjectLeaderDTO;

@Component
public class ProjectLeaderValidator {
  @Autowired
  private Validator validator;

  public List<String> validateProjectLeader(ProjectLeaderDTO projectLEaderDTO) {

    final Set<ConstraintViolation<ProjectLeaderDTO>> errors = validator.validate(projectLEaderDTO);
    if (!errors.isEmpty()) {
      throw new ConstraintViolationException("ProjectLeaderDTO is not valid", errors);
    }

    // Add business validations if any
    final List<String> customErrors = new ArrayList<>();
    return customErrors;

  }
}
