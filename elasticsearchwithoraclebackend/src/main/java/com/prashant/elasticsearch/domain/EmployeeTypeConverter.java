package com.prashant.elasticsearch.domain;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.prashant.elasticsearch.domain.EmployeeType;

@Converter(autoApply = true)
public class EmployeeTypeConverter implements AttributeConverter<EmployeeType, String> {

  @Override
  public String convertToDatabaseColumn(EmployeeType attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.name();
  }

  @Override
  public EmployeeType convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }

    return Stream.of(EmployeeType.values()).filter(c -> c.name().equalsIgnoreCase(dbData))
      .findFirst().orElseThrow(IllegalArgumentException::new);
  }
}
