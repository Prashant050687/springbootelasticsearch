package com.prashant.elasticsearch.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContractTypeDTO implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 891738812890722445L;

  private Long id;

  private String type;

  public ContractTypeDTO(Long id, String type) {
    this.id = id;
    this.type = type;
  }

}
