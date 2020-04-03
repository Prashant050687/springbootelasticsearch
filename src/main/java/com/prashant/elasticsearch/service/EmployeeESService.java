package com.prashant.elasticsearch.service;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.prashant.elasticsearch.domain.exception.ResourceNotFoundException;
import com.prashant.elasticsearch.dto.EmployeeES;
import com.prashant.elasticsearch.es.repo.EmployeeESRepo;
import com.prashant.elasticsearch.filter.dto.ESSearchFilter;
import com.prashant.elasticsearch.service.helper.FilterBuilderHelper;

@Service
public class EmployeeESService {

  private final EmployeeESRepo employeeESRepo;

  @Autowired
  private ElasticsearchTemplate elasticSearchTemplate;

  @Autowired
  public EmployeeESService(EmployeeESRepo employeeESRepo) {
    this.employeeESRepo = employeeESRepo;
  }

  public EmployeeES saveEmployee(EmployeeES employee) {
    return employeeESRepo.save(employee);
  }

  public EmployeeES findById(Long id) {
    return employeeESRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", id));
  }

  public Iterable<EmployeeES> findAll() {
    return employeeESRepo.findAll();
  }

  public void deleteEmployee(Long id) {
    EmployeeES employee = findById(id);
    employeeESRepo.delete(employee);
  }

  /**
   * Returns list of records matching the search criteria
   * @param esSearchFilter
   * @return List<EmployeeES>
   */
  public List<EmployeeES> searchEmployeeByCriteria(ESSearchFilter esSearchFilter) {
    QueryBuilder query = FilterBuilderHelper.build(esSearchFilter);
    NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(query).build();

    return elasticSearchTemplate.queryForList(nativeSearchQuery, EmployeeES.class);
  }

  /**
   * Returns pageable response based on the search criteria
   * @param esSearchFilter
   * @param pageable
   * @return Page<EmployeeES>
   */
  public Page<EmployeeES> searchEmployeeByCriteria(ESSearchFilter esSearchFilter, Pageable pageable) {
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
