package com.prashant.elasticsearch.service;

import java.util.List;

import com.prashant.elasticsearch.domain.EmployeeType;

public interface EmployeeServiceType<TDTO, T> {
  public EmployeeType getEmployeeType();

  public TDTO saveEmployee(String json);

  public TDTO saveEmployee(TDTO tdto);

  public TDTO getEmployeeById(Long id);

  public TDTO getEmployeeDTO(String json);

  public List<TDTO> findAllEmployeesByIds(List<Long> ids);

  public List<TDTO> findAllEmployees();

  public void deleteEmployee(Long id);
}
