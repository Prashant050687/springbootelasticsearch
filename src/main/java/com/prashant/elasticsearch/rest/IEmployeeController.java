package com.prashant.elasticsearch.rest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.prashant.elasticsearch.domain.EmployeeType;
import com.prashant.elasticsearch.dto.EmployeeDTO;
import com.prashant.elasticsearch.dto.EmployeeES;
import com.prashant.elasticsearch.filter.dto.ESSearchFilter;

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
  ResponseEntity<List<? extends EmployeeDTO>> findAllEmployees(@Parameter(hidden = true) @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
    @RequestParam(name = "employeeType", defaultValue = "STANDARD_EMPLOYEE") final EmployeeType employeeType);

  @Operation(
    summary = "Get Employee By Id",
    description = "Allow to get Employees by Id",
    method = "GET",

    responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved Employee data",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = EmployeeDTO.class)))})
  ResponseEntity<? extends EmployeeDTO> getEmployeeById(@PathVariable(name = "id") final Long assetId,
    @RequestParam(name = "employeeType", defaultValue = "STANDARD_EMPLOYEE") final EmployeeType employeeType);

  @Operation(
    summary = "Save Employee",
    description = "Allow to save Employees",
    method = "POST",

    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json",
      schema = @Schema(implementation = EmployeeDTO.class)),
      required = true),

    responses = {
      @ApiResponse(responseCode = "200", description = "Successfully saved Employee data",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = EmployeeDTO.class)))})
  ResponseEntity<? extends EmployeeDTO> saveEmployee(@RequestBody final String json,
    @RequestParam(name = "employeeType", defaultValue = "STANDARD_EMPLOYEE") final EmployeeType employeeType);

  @Operation(
    summary = "Delete Employee By Id",
    description = "Allow to delte Employees by Id",
    method = "DELETE",

    responses = {
      @ApiResponse(responseCode = "200", description = "Successfully deleted Employee data",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = Void.class)))})
  HttpEntity<Void> deleteEmployeeById(@PathVariable(name = "id") final Long id,
    @RequestParam(name = "employeeType", defaultValue = "STANDARD_EMPLOYEE") final EmployeeType employeeType);

  @Operation(
    summary = "Search Employees ",
    description = "Allow to search employee in Elastic search (with pagination)",
    method = "POST",
    parameters = {
      @Parameter(name = "page", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0"),
        description = "Results page you want to retrieve (0..N)"),
      @Parameter(name = "size", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"),
        description = "Number of records per page. "),
      @Parameter(name = "sort", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "id,asc"),
        description = "Sorting criteria in the format: property,asc|dec. " +
          "Default sort order is ascending.")
    },
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json",
      schema = @Schema(implementation = ESSearchFilter.class)),
      required = true),
    responses = {
      @ApiResponse(responseCode = "200", description = "Successfully Searched Employee data",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = EmployeeES.class)))})
  ResponseEntity<Page<EmployeeES>> searchEmployees(@Parameter(hidden = true) @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
    @RequestBody ESSearchFilter esSearchFilter);

  @Operation(
    summary = "Search Employees ",
    description = "Allow to search employee in Elastic search (without pagination)",
    method = "POST",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json",
      schema = @Schema(implementation = ESSearchFilter.class)),
      required = true),

    responses = {
      @ApiResponse(responseCode = "200", description = "Successfully Searched Employee data",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = EmployeeES.class)))})
  ResponseEntity<List<EmployeeES>> searchEmployees(
    @RequestBody ESSearchFilter esSearchFilter);

}