package com.prashant.elasticsearch.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BaseAuditDTO {

  int version;
  LocalDate createdDate;
}
