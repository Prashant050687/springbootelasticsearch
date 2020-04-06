package com.prashant.elasticsearch.service.helper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import com.prashant.elasticsearch.filter.dto.ESFilterCondition;
import com.prashant.elasticsearch.filter.dto.ESSearchFilter;

public class FilterBuilderHelper {
  public static QueryBuilder build(ESSearchFilter esSearchFilter) {
    BoolQueryBuilder boolParentQueryBuilder = new BoolQueryBuilder();

    Map<String, List<ESFilterCondition>> filtersByFieldName = esSearchFilter.getConditions().stream().collect(Collectors.groupingBy((ESFilterCondition::getFieldName)));

    for (Map.Entry<String, List<ESFilterCondition>> entry : filtersByFieldName.entrySet()) {
      List<ESFilterCondition> fieldConditions = entry.getValue();
      String fieldName = entry.getKey();

      if (fieldName.contains(".")) {
        // nested search. First identify the root element
        BoolQueryBuilder boolFieldQueryBuilder = new BoolQueryBuilder();
        int lastIndex = fieldName.lastIndexOf(".");
        String rootPath = fieldName.substring(0, lastIndex);
        for (ESFilterCondition condition : fieldConditions) {
          boolFieldQueryBuilder.should(QueryBuilderHelper.prepareQueryCondition(condition));
        }
        NestedQueryBuilder nestedQuery = new NestedQueryBuilder(rootPath, boolFieldQueryBuilder, ScoreMode.None);
        boolParentQueryBuilder.must(nestedQuery);

      } else {
        BoolQueryBuilder boolFieldQueryBuilder = new BoolQueryBuilder();
        for (ESFilterCondition condition : fieldConditions) {
          boolFieldQueryBuilder.should(QueryBuilderHelper.prepareQueryCondition(condition));
        }
        boolParentQueryBuilder.must(boolFieldQueryBuilder);
      }

    }

    return boolParentQueryBuilder;
  }
}
