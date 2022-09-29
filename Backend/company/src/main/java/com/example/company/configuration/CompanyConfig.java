package com.example.company.configuration;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.company.model.Company;
import com.example.company.repository.CompanyRepository;

@Configuration
public class CompanyConfig {
    
    @Bean
    CommandLineRunner commandLineRunner(CompanyRepository repository) {
        return args -> {
            
            Company company1 = new Company("Apple", "Cupertino", "apple@apple.com");
            Company company2 = new Company("Cognizant", "Bangalore - 1", "cognizant@cognizant.com");

            repository.saveAll(List.of(company1, company2));
        };
    }
}
