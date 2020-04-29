package com.prashant.elasticsearch.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Error Detail response")
@NoArgsConstructor
public class ErrorDetail {

  @Schema(description = "Error Detail Title", required = true, example = "Resource not found")
  private String title;

  @Schema(description = "Error Detail Description", required = true, example = "Requested resource cannot be found")
  private String detail;

  @Schema(description = "HTTP Status Code", example = "404")
  private Integer status;

  @Schema(description = "List of additional errors")
  private List<ErrorDetail> errors;

  public ErrorDetail(final String title, final String detail) {
    this.title = title;
    this.detail = detail;
  }

}
