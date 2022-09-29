package com.example.company.service;

import com.example.company.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.company.repository.CompanyRepository;

import lombok.extern.slf4j.Slf4j;

import com.example.company.model.Company;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

@Slf4j
@Service
public class CompanyService {

    private ResponseModel responseModel;
    private CompanyRepository companyRepository;
    private RestTemplate restTemplate;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, RestTemplate restTemplate, ResponseModel responseModel){
        this.companyRepository = companyRepository;
        this.restTemplate = restTemplate;
        this.responseModel = responseModel;
    }

    public ResponseModel getCompany(Long companyId){
        Optional<Company> company = companyRepository.findById(companyId);

        if(company.isPresent()){
            log.info("getting company");
            responseModel.payloadConstruction(company, "company fetched", "");
            return responseModel;
        }

        log.warn("company with id = " + companyId + " doesn't exist");
        responseModel.payloadConstruction("", "company with id = " + companyId + " doesn't exist", "");
        return responseModel;
    }

    public ResponseModel getCompanies(){
        log.info("getting companies");
        responseModel.payloadConstruction(companyRepository.findAll(), "companies fetched", "");
        return responseModel;
    }
    
//    public ResponseModel getMultipleCompanies(Long[] companyIds){
//        log.info("getting multiple companies");
//
//        List<Long> companyIdsList = Arrays.asList(companyIds);
//        responseModel.payloadConstruction(companyRepository.findAllById(companyIdsList), "companies fetched", "");
//        return responseModel;
//    }

    public ResponseModel createCompany(Company company){
        Optional<Company> companyByEmail = companyRepository.findCompanyByEmail(company.getEmail());

        if(companyByEmail.isPresent()){
            log.warn("company with email = " + company.getEmail() + " already exist");
            responseModel.payloadConstruction("","company with email = " + company.getEmail() + " already exist","");
            return responseModel;
        }

        log.info("creating company");
        companyRepository.save(company);
        responseModel.payloadConstruction("","company created","");
        return responseModel;
    }

    @Transactional
    public ResponseModel updateCompany(Long companyId, String companyName, String location, String email) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);

        if(!optionalCompany.isPresent()){
            log.warn("company with id = " + companyId + " doesn't exist");
            responseModel.payloadConstruction("","company with id = " + companyId + " doesn't exist","");
            return responseModel;
        }

        Company company = companyRepository.findById(companyId).orElse(null);

        log.info("updating company with id = " + companyId);

        if (email != null && email.length() > 0 && !Objects.equals(company.getEmail(), email)) {
            Optional<Company> companyByEmail = companyRepository.findCompanyByEmail(email);

            if (companyByEmail.isPresent()) {
                log.warn("company with email = " + email + " already exist");
                responseModel.payloadConstruction("","company with email = " + email + " already exist","");
                return responseModel;
            }
            company.setEmail(email);
        }

        if (companyName != null && companyName.length() > 0 && !Objects.equals(company.getCompanyName(), companyName)) {
            company.setCompanyName(companyName);
        }

        if (location != null && location.length() > 0 && !Objects.equals(company.getLocation(), location)) {
            company.setLocation(location);
        }

        responseModel.payloadConstruction("","company updated","");
        return responseModel;
    }

    public ResponseModel deleteCompany(Long companyId){
        Company company = companyRepository.findById(companyId).orElse(null);

        if(company != null){
            log.info("deleting all jobs of these company");

            ResponseEntity<Void> res = restTemplate.exchange(
                "http://localhost:8002/job/v1/deleteJobsByCompanyId/{companyId}/",
                HttpMethod.DELETE,
                new HttpEntity<String>(""),
                Void.class, company.getCompanyId());
            
            if(res.getStatusCodeValue() == 200){
                log.info("deleting company with id = " + companyId);
                companyRepository.deleteById(companyId);
                responseModel.payloadConstruction("","company deleted","");
                return responseModel;
            }

            log.warn("unable to delete jobs of companyId = " + companyId);
            responseModel.payloadConstruction("","unable to delete jobs of companyId = " + companyId,"");
            return responseModel;
        }

        log.warn("company with id = " + companyId + " doesn't exist");
        responseModel.payloadConstruction("","company with id = " + companyId + " doesn't exist","");
        return responseModel;
    }

}
