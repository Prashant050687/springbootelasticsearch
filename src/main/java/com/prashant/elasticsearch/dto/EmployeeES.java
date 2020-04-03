package com.prashant.elasticsearch.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.prashant.elasticsearch.domain.EmployeeType;

import lombok.Data;

@Data
@Document(indexName = "employee_index", type = "employee")
public class EmployeeES implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 544120913140064729L;

  @Id
  private Long id;

  private String firstName;

  private String lastName;

  private Double salary;

  private ContractTypeDTO contractType;

  @Enumerated(EnumType.STRING)
  EmployeeType employeeType;

  @Field(type = FieldType.Date)
  LocalDate createdDate;
}
