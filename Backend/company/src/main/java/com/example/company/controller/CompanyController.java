package com.example.company.controller;

import com.example.company.model.ResponseModel;
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

import com.example.company.model.Company;
import com.example.company.service.CompanyService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/company/v1")
public class CompanyController {

    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }
    
    @GetMapping("/{companyId}/")
    public ResponseModel getCompany(@PathVariable("companyId") Long companyId){
        return companyService.getCompany(companyId);
    }

    @GetMapping("/")
    public ResponseModel getCompanies(){
        return companyService.getCompanies();
    }

//    @GetMapping("/multiple/")
//    public ResponseModel getMultipleCompanies(@RequestParam(required = true) Long[] companyIds){
//        return companyService.getMultipleCompanies(companyIds);
//    }

    @PostMapping("/")
    public ResponseModel createCompany(@RequestBody Company company){
        return companyService.createCompany(company);
    }

    @PutMapping("/{companyId}/")
    public ResponseModel updateCompany(
        @PathVariable("companyId") Long companyId, 
        @RequestParam(required = true) String companyName,
        @RequestParam(required = true) String location,
        @RequestParam(required = true) String email){

        return companyService.updateCompany(companyId, companyName, location, email);
    }

    @DeleteMapping("/{companyId}")
    public ResponseModel deleteCompany(@PathVariable("companyId") Long companyId){
        return companyService.deleteCompany(companyId);
    }

}
