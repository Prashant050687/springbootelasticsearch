package com.prashant.elasticsearch.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.elasticsearch.annotations.Document;

import com.prashant.elasticsearch.domain.EmployeeType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(indexName = "employee_index", type = "employee")
public class EmployeeDTO extends BaseAuditDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 544120913140064729L;

  private Long id;

  @NotNull(message = "First Name cannot be null")
  @NotBlank(message = "First Name cannot be Empty")
  private String firstName;

  @NotNull(message = "Last Name cannot be null")
  @NotBlank(message = "Last Name cannot be Empty")
  private String lastName;

  private Double salary;

  @NotNull(message = "Contract Type cannot be null")
  private ContractTypeDTO contractType;

  @NotNull(message = "Employee Type cannot be null")
  EmployeeType employeeType;

  private String imagePath;
}
