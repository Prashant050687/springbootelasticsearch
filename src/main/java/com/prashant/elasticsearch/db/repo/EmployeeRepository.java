package com.prashant.elasticsearch.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.elasticsearch.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
