package com.example.job.VO;

import com.example.job.model.Job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobCompanyTemplate {
    private Job job;
    private Company company;
}
