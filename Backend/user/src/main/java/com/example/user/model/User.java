package com.example.user.model;

import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private Long[] appliedJobIds;
    private LocalDate dob;
    private String[] skills;
    private Integer experienceYears;
    private Integer experienceMonths;
    private Integer currentCtcInLakhs;
    private Long companyId;

    public User(String firstName, String lastName, String email, Long[] appliedJobIds, LocalDate dob, 
        String[] skills, Integer experienceYears, Integer experienceMonths, Integer currentCtcInLakhs,
        Long companyId){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.appliedJobIds = appliedJobIds;
        this.dob = dob;
        this.skills = skills;
        this.experienceYears = experienceYears;
        this.experienceMonths = experienceMonths;
        this.currentCtcInLakhs = currentCtcInLakhs;
        this.companyId = companyId;
    }

    @Transient
    private Integer age;

    public Integer getAge(){
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "User { id = " + userId + " }";
    }

}
