package com.prashant.elasticsearch.service;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.prashant.elasticsearch.domain.exception.ResourceNotFoundException;
import com.prashant.elasticsearch.dto.EmployeeES;
import com.prashant.elasticsearch.es.repo.EmployeeESRepo;

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

  public List<EmployeeES> searchMultiField(String firstName, String lastName) {
    QueryBuilder query = QueryBuilders.boolQuery()
      .must(QueryBuilders.matchQuery("firstName", firstName))
      .must(QueryBuilders.matchQuery("lastName", lastName));

    NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(query).build();
    return elasticSearchTemplate.queryForList(nativeSearchQuery, EmployeeES.class);
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
