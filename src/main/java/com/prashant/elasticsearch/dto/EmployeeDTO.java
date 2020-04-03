package com.prashant.elasticsearch.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.prashant.elasticsearch.domain.EmployeeType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeDTO extends BaseAuditDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 544120913140064729L;

  private Long id;

  @NotNull(message = "First Name cannot be null")
  private String firstName;

  @NotNull(message = "Last Name cannot be null")
  private String lastName;

  private Double salary;

  @NotNull(message = "Contract Type cannot be null")
  private ContractTypeDTO contractType;

  @NotNull(message = "Employee Type cannot be null")
  EmployeeType employeeType;
}
