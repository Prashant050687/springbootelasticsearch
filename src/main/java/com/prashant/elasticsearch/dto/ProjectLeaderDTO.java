package com.prashant.elasticsearch.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectLeaderDTO extends EmployeeDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 544120913140064729L;
  @NotNull
  String projectName;

  Integer reportingEmployees;

}
