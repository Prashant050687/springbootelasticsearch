package com.prashant.elasticsearch.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CONTRACT_TYPE")
@NoArgsConstructor
@Data
public class ContractType {
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "TYPE_OF_CONTRACT")
  private String type;

  public ContractType(Long id, String type) {
    this.id = id;
    this.type = type;
  }

}
