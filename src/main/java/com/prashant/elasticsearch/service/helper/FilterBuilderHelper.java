package com.prashant.elasticsearch.service.helper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import com.prashant.elasticsearch.filter.dto.ESFilterCondition;
import com.prashant.elasticsearch.filter.dto.ESSearchFilter;

public class FilterBuilderHelper {
  public static QueryBuilder build(ESSearchFilter esSearchFilter) {
    BoolQueryBuilder boolParentQueryBuilder = new BoolQueryBuilder();

    Map<String, List<ESFilterCondition>> filtersByFieldName = esSearchFilter.getConditions().stream().collect(Collectors.groupingBy((ESFilterCondition::getFieldName)));

    for (Map.Entry<String, List<ESFilterCondition>> entry : filtersByFieldName.entrySet()) {
      BoolQueryBuilder boolFieldQueryBuilder = new BoolQueryBuilder();
      List<ESFilterCondition> fieldConditions = entry.getValue();
      String fieldName = entry.getKey();
      for (ESFilterCondition condition : fieldConditions) {
        boolFieldQueryBuilder.should(QueryBuilderHelper.prepareSimpleCondition(condition));
      }
      boolParentQueryBuilder.must(boolFieldQueryBuilder);
    }

    return boolParentQueryBuilder;
  }
}
