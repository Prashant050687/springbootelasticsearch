package com.prashant.elasticsearch.filter.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ESSearchFilter implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 7378417837577655453L;

  @NotNull
  @Size(min = 1)
  private List<ESFilterCondition> conditions = new ArrayList<>();

}
