package com.example.user.controller;

import java.time.LocalDate;

import com.example.user.model.LoginRequestModel;
import com.example.user.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.example.user.service.UserService;
import com.example.user.model.User;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/user/v1")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    
    @GetMapping("/{userId}/")
    public ResponseModel getUser(@PathVariable("userId") Long userId){
        return userService.getUser(userId);
    }

    @PostMapping("/")
    public ResponseModel createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/{userId}/")
    public ResponseModel updateUser(
        @PathVariable("userId") Long userId, 
        @RequestParam(required = true) String firstName,
        @RequestParam(required = true) String lastName,
        @RequestParam(required = true) String email,
        @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
        @RequestParam(required = true) String skills,
        @RequestParam(required = true) Integer experienceYears,
        @RequestParam(required = true) Integer experienceMonths,
        @RequestParam(required = true) Integer currentCtcInLakhs,
        @RequestParam(required = true) Long companyId){

        return userService.updateUser(userId, firstName, lastName, email, dob, skills, experienceYears, experienceMonths,
                currentCtcInLakhs, companyId);
    }

    @DeleteMapping("/{userId}/")
    public ResponseModel deleteUser(@PathVariable("userId") Long userId){
        return userService.deleteUser(userId);
    }

    @PostMapping("/login/")
    public ResponseModel loginUser(@RequestBody LoginRequestModel loginRequestModel){
        return userService.loginUser(loginRequestModel.getEmail(), loginRequestModel.getPassword());
    }

    @PutMapping("/applyJob/{userId}/{jobId}/")
    public ResponseModel applyJob(@PathVariable("userId") Long userId, @PathVariable("jobId") Long jobId){
        return userService.applyJob(userId, jobId);
    }
}
