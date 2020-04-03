package com.prashant.elasticsearch.service.helper;

import java.util.ArrayList;
import java.util.Collections;
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

    // Grouping Filter conditions by field name
    Map<String, List<ESFilterCondition>> filtersByFieldName = esSearchFilter.getConditions().stream()
      .collect(Collectors.toMap(
        ESFilterCondition::getFieldName,
        Collections::singletonList,
        (List<ESFilterCondition> oldList, List<ESFilterCondition> newEl) -> {
          List<ESFilterCondition> newList = new ArrayList<>(oldList.size() + 1);
          newList.addAll(oldList);
          newList.addAll(newEl);
          return newList;
        }));

    for (Map.Entry<String, List<ESFilterCondition>> entry : filtersByFieldName.entrySet()) {
      BoolQueryBuilder boolFieldQueryBuilder = new BoolQueryBuilder();
      List<ESFilterCondition> fieldConditions = entry.getValue();
      for (ESFilterCondition condition : fieldConditions) {
        boolFieldQueryBuilder.should(QueryBuilderHelper.prepareSimpleCondition(condition));
      }
      boolParentQueryBuilder.must(boolFieldQueryBuilder);
    }

    return boolParentQueryBuilder;
  }
}
