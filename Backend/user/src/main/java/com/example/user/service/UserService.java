package com.example.user.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import com.example.user.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.user.model.User;
import com.example.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

    private UserRepository userRepository;
    private ResponseModel responseModel;

    @Autowired
    public UserService(UserRepository userRepository, ResponseModel responseModel){
        this.userRepository = userRepository;
        this.responseModel = responseModel;
    }

    public ResponseModel getUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            log.info("getting user");
            responseModel.payloadConstruction(user, "user fetched", "");
            return responseModel;
        }

        log.warn("user with id = " + userId + " doesn't exist");
        responseModel.payloadConstruction("", "user with id = " + userId + " doesn't exist", "");
        return responseModel;
    }

    public ResponseModel createUser(User user) {
        Optional<User> userByEmail = userRepository.findUserByEmail(user.getEmail());

        if(userByEmail.isPresent()){
            log.warn("user with email = " + user.getEmail() + " already exist");
            responseModel.payloadConstruction("","user with email = " + user.getEmail() + " already exist","");
            return responseModel;
        }

        log.info("creating user");
        userRepository.save(user);
        responseModel.payloadConstruction("","user created","");
        return responseModel;
    }

    @Transactional
    public ResponseModel updateUser(Long userId, String firstName, String lastName, String email, LocalDate dob,
                                    String skills, Integer experienceYears, Integer experienceMonths,
                                    Integer currentCtcInLakhs, Long companyId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if(!optionalUser.isPresent()){
            log.warn("user with id = " + userId + " doesn't exist");
            responseModel.payloadConstruction("","user with id = " + userId + " doesn't exist","");
            return responseModel;
        }

        User user = userRepository.findById(userId).orElse(null);

        log.info("updating user with id = " + userId);

        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {
            Optional<User> userByEmail = userRepository.findUserByEmail(email);

            if (userByEmail.isPresent()) {
                log.warn("user with email = " + email + " already exist");
                responseModel.payloadConstruction("","user with email = " + email + " already exist","");
                return responseModel;
            }
            user.setEmail(email);
        }

        if (firstName != null && firstName.length() > 0 && !Objects.equals(user.getFirstName(), firstName)) {
            user.setFirstName(firstName);
        }

        if (lastName != null && lastName.length() > 0 && !Objects.equals(user.getLastName(), lastName)) {
            user.setLastName(lastName);
        }

        if (dob != null && !Objects.equals(user.getDob(), dob)) {
            user.setDob(dob);
        }

        if (experienceYears != null && !Objects.equals(user.getExperienceYears(), experienceYears)) {
            user.setExperienceYears(experienceYears);
        }

        if (experienceMonths != null && !Objects.equals(user.getExperienceMonths(), experienceMonths)) {
            user.setExperienceMonths(experienceMonths);
        }

        if (currentCtcInLakhs != null && !Objects.equals(user.getCurrentCtcInLakhs(), currentCtcInLakhs)) {
            user.setCurrentCtcInLakhs(currentCtcInLakhs);
        }

        //skills
        if(skills != "") {
            String[] oldSkills = user.getSkills();
            Integer i = 0;
            Boolean flag = false;
            for( ; i<oldSkills.length ; i++){
                if(skills.equalsIgnoreCase(oldSkills[i])) {
                    flag = true;
                    break;
                }
            }

            if(!flag) {
                String[] newSkills = new String[oldSkills.length + 1];
                i = 0;
                for( ; i<oldSkills.length ; i++){
                    newSkills[i] = oldSkills[i];
                }
                newSkills[i] = skills;
                user.setSkills(newSkills);
            }
        }

        user.setCompanyId(companyId);

        responseModel.payloadConstruction("","user updated","");
        return responseModel;
    }

    public ResponseModel deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            log.info("deleting user with id = " + userId);
            userRepository.deleteById(userId);
            responseModel.payloadConstruction("","user deleted","");
            return responseModel;
        }

        log.warn("user with id = " + userId + " doesn't exist");
        responseModel.payloadConstruction("", "user with id = " + userId + " doesn't exist", "");
        return responseModel;
    }

    public ResponseModel loginUser(String email, String password) {
        User userByEmail = userRepository.findUserByEmail(email).orElse(null);

        if(userByEmail != null) {
            log.warn("user logged in");
            responseModel.payloadConstruction(userByEmail.getUserId(),"user logged in","");
            return responseModel;
        }

        log.warn("user with email = " + email + " doesn't exist");
        responseModel.payloadConstruction("","user with email = " + email + " doesn't exist","");
        return responseModel;
    }

    @Transactional
    public ResponseModel applyJob(Long userId, Long jobId) {
        User user = userRepository.findById(userId).orElse(null);

        if(user != null){
            log.info("user applying for jobId = " + jobId);
            Long[] appliedJobs = user.getAppliedJobIds();

            for(Long job: appliedJobs){
                if(job.equals(jobId)){
                    log.warn("job already applied");
                    responseModel.payloadConstruction("", "already applied for this job", "");
                    return responseModel;
                }
            }

            Long[] newAppliedJobs = new Long[appliedJobs.length + 1];
            Integer i = 0;
            for( ; i<appliedJobs.length ; i++){
                newAppliedJobs[i] = appliedJobs[i];
            }
            newAppliedJobs[i] = jobId;

            user.setAppliedJobIds(newAppliedJobs);
            log.info("user applied for jobId = " + jobId);
            responseModel.payloadConstruction("", "user applied for jobId = " + jobId, "");
            return responseModel;
        }

        log.warn("user with id = " + userId + " doesn't exist");
        responseModel.payloadConstruction("", "user with id = " + userId + " doesn't exist", "");
        return responseModel;
    }
    
}
