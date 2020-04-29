package com.prashant.elasticsearch.service.helper;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.http.HttpStatus;

import com.prashant.elasticsearch.domain.exception.BusinessException;
import com.prashant.elasticsearch.filter.dto.ESFilterCondition;

public class QueryBuilderHelper {
  public static QueryBuilder prepareQueryCondition(ESFilterCondition condition) {
    QueryBuilder queryStringBuilder;
    switch (condition.getOperation()) {
      case EQ:
        queryStringBuilder = QueryBuilders.matchQuery(condition.getFieldName(), condition.getValue1());
        break;
      case GT:
        queryStringBuilder = QueryBuilders.rangeQuery(condition.getFieldName()).gt(condition.getValue1());
        break;
      case LT:
        queryStringBuilder = QueryBuilders.rangeQuery(condition.getFieldName()).lt(condition.getValue1());
        break;
      case GTE:
        queryStringBuilder = QueryBuilders.rangeQuery(condition.getFieldName()).gte(condition.getValue1());
        break;
      case LTE:
        queryStringBuilder = QueryBuilders.rangeQuery(condition.getFieldName()).lte(condition.getValue1());
        break;
      case BETWEEN:
        queryStringBuilder = QueryBuilders.rangeQuery(condition.getFieldName()).from(condition.getValue1()).to(condition.getValue2());
        break;
      case LIKE:
        queryStringBuilder = queryStringQuery("*" + QueryParser.escape(condition.getValue1()) + "*").field(condition.getFieldName());
        break;
      case STARTS_WITH:
        queryStringBuilder = QueryBuilders.prefixQuery(condition.getFieldName(), QueryParser.escape(condition.getValue1()));
        break;
      case REGEX:
        queryStringBuilder = QueryBuilders.regexpQuery(condition.getFieldName(), condition.getValue1());
      default:
        throw new BusinessException("Invalid search Operation", "search operation " + condition.getOperation() + "not supported ", HttpStatus.BAD_REQUEST.value());
    }
    return queryStringBuilder;
  }
}
