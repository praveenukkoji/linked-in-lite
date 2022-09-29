package com.example.user.configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.user.model.User;
import com.example.user.repository.UserRepository;

@Configuration
public class UserConfig {
    
    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            
            Long[] appliedJobIds1 = {1L};
            String[] skills1 = {"Java", "Spring Boot"};
            User user1 = new User(
                "Praveen", 
                "Ukkoji", 
                "praveen@gmail.com", 
                appliedJobIds1, 
                LocalDate.of(2000, Month.SEPTEMBER, 20), 
                skills1, 
                0, 
                6,
                7, 
                1L);
            
            Long[] appliedJobIds2 = {1L, 2L};
            String[] skills2 = {"Angular Js", "Python"};
            User user2 = new User(
                "Prem", 
                "Ukkoji", 
                "prem@gmail.com", 
                appliedJobIds2, 
                LocalDate.of(1997, Month.FEBRUARY, 17), 
                skills2, 
                3, 
                3, 
                8, 
                2L);

            repository.saveAll(List.of(user1, user2));
        };
    }
}

