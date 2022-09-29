package com.example.company.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.company.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{

    @Query("SELECT c FROM Company c WHERE c.email = ?1")
    Optional<Company> findCompanyByEmail(String email);
    
}
