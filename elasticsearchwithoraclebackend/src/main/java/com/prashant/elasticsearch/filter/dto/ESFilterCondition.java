package com.prashant.elasticsearch.filter.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class is used to keep information about conditions. Possible values: LIKE, BETWEEN, EQ, GT, LT, GTE, LTE
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ESFilterCondition implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -952812205197717269L;

  @NotNull
  private String fieldName;

  // Filter operation. Possible values: LIKE, BETWEEN, EQ, GT, LT, GTE, LTE
  @NotNull
  private FilterOperation operation;

  private String value1;

  // To be used when between is used
  private String value2;

}
