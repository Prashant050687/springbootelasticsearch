package com.prashant.elasticsearch.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.elasticsearch.domain.ProjectLeader;

public interface ProjectLeadRepository extends JpaRepository<ProjectLeader, Long> {

}
