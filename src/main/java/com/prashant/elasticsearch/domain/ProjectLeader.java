package com.prashant.elasticsearch.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "PROJECT_LEAD")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectLeader extends Employee {
  /**
  * 
  */
  private static final long serialVersionUID = 4893421419131682237L;

  @Column(name = "PROJECT_NAME")
  String projectName;

  @Column(name = "REPORTING_EMP_COUNT")
  Integer reportingEmployees;

}
