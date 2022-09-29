package com.example.job.controller;

import com.example.job.model.Job;
import com.example.job.model.ResponseModel;
import com.example.job.service.JobService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/job/v1")
public class JobController {

    private JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

//    @GetMapping("/{jobId}/")
//    public ResponseModel getJobWithCompany(@PathVariable("jobId") Long jobId) throws IOException {
//        return jobService.getJobWithCompany(jobId);
//    }

    @GetMapping("/multiple/")
    public ResponseModel getMultipleJobs(@RequestParam(required = true) Long[] jobIds){
        return jobService.getMultipleJobs(jobIds);
    }

    @GetMapping("/")
    public ResponseModel getJobs() {
        return jobService.getJobs();
    }

    @PostMapping("/")
    public ResponseModel createJob(@RequestBody Job job) {
        return jobService.createJob(job);
    }

    @PutMapping("/{jobId}/")
    public ResponseModel updateJob(
            @PathVariable("jobId") Long jobId,
            @RequestParam(required = true) String jobRole,
            @RequestParam(required = true) String jobDescription,
            @RequestParam(required = true) String[] requiredSkills,
            @RequestParam(required = true) Integer fromExperienceInYears,
            @RequestParam(required = true) Integer toExperienceInYears,
            @RequestParam(required = true) Integer fromCtcInLakhs,
            @RequestParam(required = true) Integer toCtcInLakhs) {

        return jobService.updateJob(
                jobId, jobRole, jobDescription,
                requiredSkills, fromExperienceInYears, toExperienceInYears,
                fromCtcInLakhs, toCtcInLakhs);
    }

    @DeleteMapping("/{jobId}/")
    public ResponseModel deleteJob(@PathVariable("jobId") Long jobId) {
        return jobService.deleteJob(jobId);
    }

    @DeleteMapping("/deleteJobsByCompanyId/{companyId}/")
    public ResponseModel deleteJobsByCompanyId(@PathVariable("companyId") Long companyId) {
        return jobService.deleteJobsByCompanyId(companyId);
    }
}
