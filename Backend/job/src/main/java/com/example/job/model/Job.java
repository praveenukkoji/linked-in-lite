package com.example.job.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Job {

    @Id
    @SequenceGenerator(name = "job_sequence", sequenceName = "job_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_sequence")
    private Long jobId;

    private String jobRole;
    private String jobDescription;
    private String[] requiredSkills;
    private Integer fromExperienceInYears;
    private Integer toExperienceInYears;
    private Integer fromCtcInLakhs;
    private Integer toCtcInLakhs;
    private Long companyId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdOn;

    public Job(String jobRole, String jobDescription, String[] requiredSkills,
            Integer fromExperienceInYears, Integer toExperienceInYears,
            Integer fromCtcInLakhs, Integer toCtcInLakhs, Long companyId) {
        this.jobRole = jobRole;
        this.jobDescription = jobDescription;
        this.requiredSkills = requiredSkills;
        this.fromExperienceInYears = fromExperienceInYears;
        this.toExperienceInYears = toExperienceInYears;
        this.fromCtcInLakhs = fromCtcInLakhs;
        this.toCtcInLakhs = toCtcInLakhs;
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "Job { id = " + jobId + " }";
    }

}
