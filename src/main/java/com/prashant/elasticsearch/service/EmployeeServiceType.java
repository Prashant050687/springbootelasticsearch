package com.prashant.elasticsearch.service;

import java.util.List;

import com.prashant.elasticsearch.domain.EmployeeType;

public interface EmployeeServiceType<TDTO, T> {
  public EmployeeType getEmployeeType();

  public TDTO saveEmployee(TDTO employeeDTO);

  public T findEmployeeById(Long id);

  public TDTO getEmployeeDTO(String json);

  public List<TDTO> findAllEmployeesByIds(List<Long> ids);

  public void deleteEmployee(Long id);
}
