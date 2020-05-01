package com.prashant.elasticsearch.service;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.prashant.elasticsearch.domain.exception.ResourceNotFoundException;
import com.prashant.elasticsearch.dto.EmployeeDTO;
import com.prashant.elasticsearch.es.repo.EmployeeESRepo;
import com.prashant.elasticsearch.filter.dto.ESSearchFilter;
import com.prashant.elasticsearch.service.helper.FilterBuilderHelper;

@Service
public class EmployeeESService {

  private final EmployeeESRepo employeeESRepo;

  @Autowired
  public EmployeeESService(EmployeeESRepo employeeESRepo) {
    this.employeeESRepo = employeeESRepo;
  }

  public EmployeeDTO saveEmployee(EmployeeDTO employee) {
    return employeeESRepo.save(employee);
  }

  public EmployeeDTO findById(String id) {
    return employeeESRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", id));
  }

  public Page<EmployeeDTO> findAll(Pageable pageable) {
    return employeeESRepo.findAll(pageable);
  }

  public void deleteEmployee(String id) {
    EmployeeDTO employee = findById(id);
    employeeESRepo.delete(employee);
  }

  /**
   * Returns pageable response based on the search criteria
   * @param esSearchFilter
   * @param pageable
   * @return Page<EmployeeES>
   */
  public Page<EmployeeDTO> searchEmployeeByCriteria(ESSearchFilter esSearchFilter, Pageable pageable) {
    QueryBuilder query = FilterBuilderHelper.build(esSearchFilter);
    NativeSearchQuery nativeSearchQuery = null;
    if (null != pageable) {
      nativeSearchQuery = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(query).build();
    } else {
      nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(query).build();
    }

    return employeeESRepo.search(nativeSearchQuery);

  }

  /*
   * public List<EmployeeES> getEmployeeSearchData(String input) {
   * String search = ".*" + input + ".*";
   * SearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(QueryBuilders.regexpQuery("firstName", search)).build();
   * return elasticSearchTemplate.queryForList(searchQuery, EmployeeES.class);
   * }
   * 
   * public List<EmployeeES> getMultiMatchSearch(String input) {
   * 
   * SearchQuery searchQuery = new NativeSearchQueryBuilder()
   * .withQuery(QueryBuilders.multiMatchQuery(input).field("firstName").field("lastName").type(MultiMatchQueryBuilder.Type.BEST_FIELDS)).
   * build();
   * return elasticSearchTemplate.queryForList(searchQuery, EmployeeES.class);
   * }
   */
}
