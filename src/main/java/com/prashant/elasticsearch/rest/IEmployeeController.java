package com.prashant.elasticsearch.rest;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import com.prashant.elasticsearch.dto.EmployeeDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface IEmployeeController {
  @Operation(
    summary = "Get Employees",
    description = "Allow to get Employees",
    method = "GET",
    parameters = {
      @Parameter(name = "page", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0"),
        description = "Results page you want to retrieve (0..N)"),
      @Parameter(name = "size", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"),
        description = "Number of records per page. "),
      @Parameter(name = "sort", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "id,asc"),
        description = "Sorting criteria in the format: property,asc|dec. " +
          "Default sort order is ascending.")
    },
    responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved Employees data",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = EmployeeDTO.class)))})
  ResponseEntity<List<EmployeeDTO>> getAllEmployees(@Parameter(hidden = true) @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable);

}
