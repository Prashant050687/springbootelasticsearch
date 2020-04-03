package com.prashant.elasticsearch.rest;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashant.elasticsearch.dto.EmployeeDTO;

@RestController
@ExposesResourceFor(EmployeeDTO.class)
@RequestMapping("/employee")
public class EmployeeController implements IEmployeeController {

  @Override
  @GetMapping(value = "")
  public ResponseEntity<List<EmployeeDTO>> getAllEmployees(Pageable pageable) {
    // TODO Auto-generated method stub
    return null;
  }

}
