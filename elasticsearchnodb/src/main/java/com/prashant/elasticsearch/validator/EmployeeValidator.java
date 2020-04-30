package com.prashant.elasticsearch.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prashant.elasticsearch.dto.EmployeeDTO;

@Component
public class EmployeeValidator {
  @Autowired
  private Validator validator;

  public List<String> validateEmployee(EmployeeDTO employeeDTO) {

    final Set<ConstraintViolation<EmployeeDTO>> errors = validator.validate(employeeDTO);
    if (!errors.isEmpty()) {
      throw new ConstraintViolationException("EmployeeDTO is not valid", errors);
    }

    // Add business validations if any
    final List<String> customErrors = new ArrayList<>();
    // TODO:validate employee type
    return customErrors;

  }
}
