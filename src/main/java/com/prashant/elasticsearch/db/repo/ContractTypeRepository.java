package com.prashant.elasticsearch.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.elasticsearch.domain.ContractType;

public interface ContractTypeRepository extends JpaRepository<ContractType, Long> {

}
