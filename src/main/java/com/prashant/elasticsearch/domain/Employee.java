package com.prashant.elasticsearch.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "EMPLOYEE")
@Data
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends BaseAuditEntity {

  /**
   * 
   */
  private static final long serialVersionUID = 6379514845832650890L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
  @SequenceGenerator(name = "sequence_generator", sequenceName = "EMPLOYEE_SEQ",
    allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "salary")
  private Double salary;

  @OneToOne
  @JoinColumn(name = "FK_CONTRACT_TYPE")
  private ContractType contractType;

  @Column(name = "EMPLOYEE_TYPE")
  private EmployeeType employeeType;
}
