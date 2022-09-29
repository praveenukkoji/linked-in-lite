package com.example.job.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import com.example.job.VO.Company;
import com.example.job.VO.JobCompanyTemplate;
import com.example.job.model.Job;
import com.example.job.model.ResponseModel;
import com.example.job.repository.JobRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JobService {

    private ResponseModel responseModel;
    private JobRepository jobRepository;
    private RestTemplate restTemplate;

    @Autowired
    public JobService(JobRepository jobRepository, RestTemplate restTemplate, ResponseModel responseModel) {
        this.jobRepository = jobRepository;
        this.restTemplate = restTemplate;
        this.responseModel = responseModel;
    }

    public ResponseModel getJobs() {
        log.info("getting jobs");
        responseModel.payloadConstruction(jobRepository.findAll(), "jobs fetched", "");
        return responseModel;
    }

    public ResponseModel getMultipleJobs(Long[] jobIds){
        log.info("getting multiple jobs");

        List<Long> jobIdsList = Arrays.asList(jobIds);
        responseModel.payloadConstruction(jobRepository.findAllById(jobIdsList), "jobs fetched", "");
        return responseModel;
    }

//    public ResponseModel getJobWithCompany(Long jobId) throws IOException {
//        Job job = jobRepository.findById(jobId).orElse(null);
//
//        if(job != null){
//            log.info("getting job");
//            JobCompanyTemplate jobCompanyTemplate = new JobCompanyTemplate();
//            ResponseModel response = restTemplate.getForObject(
//                    "http://localhost:8001/company/v1/{companyId}/", ResponseModel.class,
//                    job.getCompanyId());
//
//            jobCompanyTemplate.setJob(job);
//
//            // TODO: your part do it fast
//            Company company = new Company();
//            System.out.println("");
//
//            jobCompanyTemplate.setCompany(company);
//
//            responseModel.payloadConstruction(jobCompanyTemplate, "job with company fetched", "");
//            return responseModel;
//        }
//
//        log.warn("job with id = " + jobId + " not found");
//        responseModel.payloadConstruction("", "job with id = " + jobId + " doesn't exist", "");
//        return responseModel;
//    }

    public ResponseModel createJob(Job job) {
        log.info("creating job");

        ResponseModel company = restTemplate.getForObject(
                "http://localhost:8001/company/v1/{companyId}/", ResponseModel.class,
                job.getCompanyId());

        if (company.getPayload() != "") {
            jobRepository.save(job);
            log.info("job created");
            responseModel.payloadConstruction("", "job created", "");
            return responseModel;
        }

        log.warn("unable to create job because company with id = " + job.getCompanyId() + " doesn't exists");
        responseModel.payloadConstruction("", "unable to create job because company with id = " + job.getCompanyId() + " doesn't exists", "");
        return responseModel;
    }

    @Transactional
    public ResponseModel updateJob(Long jobId, String jobRole, String jobDescription, String[] requiredSkills,
            Integer fromExperienceInYears, Integer toExperienceInYears, Integer fromCtcInLakhs, Integer toCtcInLakhs) {

        Job job = jobRepository.findById(jobId).orElse(null);

        if (job == null) {
            log.warn("job with id = " + jobId + " doesn't exist");
            responseModel.payloadConstruction("", "job with id = " + jobId + " doesn't exist", "");
            return responseModel;
        }

        log.info("updating job with id = " + jobId);

        if (fromExperienceInYears > toExperienceInYears) {
            log.warn("unable to create job");
            log.error("from experience should be <= to experience");
            responseModel.payloadConstruction("", "from experience should be <= to experience", "");
            return responseModel;
        }

        if (fromCtcInLakhs > toCtcInLakhs) {
            log.warn("unable to create job");
            log.error("from CTC should be <= to CTC");
            responseModel.payloadConstruction("", "from CTC should be <= to CTC", "");
            return responseModel;
        }

        if (fromExperienceInYears < 0 || toExperienceInYears < 0) {
            log.warn("unable to create job");
            log.error("experience should be +ve value");
            responseModel.payloadConstruction("", "experience should be +ve value", "");
            return responseModel;
        }

        if (fromCtcInLakhs < 0 || toCtcInLakhs < 0) {
            log.warn("unable to create job");
            log.error("CTC should be +ve value");
            responseModel.payloadConstruction("", "CTC should be +ve value", "");
            return responseModel;
        }

        if (jobRole != null && jobRole.length() > 0 && !Objects.equals(job.getJobRole(), jobRole)) {
            job.setJobRole(jobRole);
        }

        if (jobDescription != null && jobDescription.length() > 0
                && !Objects.equals(job.getJobDescription(), jobDescription)) {
            job.setJobDescription(jobDescription);
        }

        if (requiredSkills != null && !Objects.equals(job.getRequiredSkills(), requiredSkills)) {
            job.setRequiredSkills(requiredSkills);
        }

        if (!Objects.equals(job.getFromExperienceInYears(), fromExperienceInYears)) {
            job.setFromExperienceInYears(fromExperienceInYears);
        }

        if (!Objects.equals(job.getToExperienceInYears(), toExperienceInYears)) {
            job.setToExperienceInYears(toExperienceInYears);
        }

        if (!Objects.equals(job.getFromCtcInLakhs(), fromCtcInLakhs)) {
            job.setFromCtcInLakhs(fromCtcInLakhs);
        }

        if (!Objects.equals(job.getToCtcInLakhs(), toCtcInLakhs)) {
            job.setToCtcInLakhs(toCtcInLakhs);
        }

        responseModel.payloadConstruction("", "job updated", "");
        return responseModel;
    }

    public ResponseModel deleteJob(Long jobId) {
        Optional<Job> job = jobRepository.findById(jobId);

        if (job.isPresent()) {
            log.info("deleting job with id = " + jobId);
            jobRepository.deleteById(jobId);
            responseModel.payloadConstruction("", "job deleted", "");
            return responseModel;
        }

        log.warn("job with id = " + jobId + " doesn't exist");
        responseModel.payloadConstruction("", "job with id = " + jobId + " doesn't exist", "");
        return responseModel;
    }

    public ResponseModel deleteJobsByCompanyId(Long companyId) {
        List<Job> jobs = jobRepository.findJobsByCompanyId(companyId);

        List<Long> jobIds = new ArrayList<>();
        jobs.forEach(job -> jobIds.add(job.getJobId()));

        log.info("deleting job of company id = " + companyId);
        jobRepository.deleteAllById(jobIds);
        responseModel.payloadConstruction("", "jobs deleted", "");
        return responseModel;
    }

}
