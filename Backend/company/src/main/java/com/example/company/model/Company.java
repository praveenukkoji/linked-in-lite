package com.example.company.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Company {

    @Id
    @SequenceGenerator(name = "company_sequence", sequenceName = "company_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_sequence")
    private Long companyId;

    private String companyName;
    private String location;
    private String email;

    public Company(String companyName, String location, String email){
        this.companyName = companyName;
        this.location = location;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Company { id = " + companyId + " }";
    }
    
}
