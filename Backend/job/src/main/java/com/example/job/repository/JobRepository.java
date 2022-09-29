package com.example.job.repository;

import java.util.List;

import com.example.job.model.Job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

    @Query(value = "SELECT j FROM Job j WHERE j.companyId = ?1")
    List<Job> findJobsByCompanyId(Long companyId);

}
