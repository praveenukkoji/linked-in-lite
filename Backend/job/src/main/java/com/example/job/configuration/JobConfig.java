package com.example.job.configuration;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.job.model.Job;
import com.example.job.repository.JobRepository;

@Configuration
public class JobConfig {

    @Bean
    CommandLineRunner commandLineRunner(JobRepository repository) {
        return args -> {
            String[] skills = {"React Js", "Spring Boot"};
            Job job1 = new Job(
                "SDE1", 
                "Full Stack Developer", 
                skills,
                2,
                3,
                12,
                23,
                1L);
            
            Job job2 = new Job(
                "SDE2", 
                "Full Stack Developer", 
                skills,
                2,
                3,
                12,
                23,
                1L);
            
            Job job3 = new Job(
                "SDE3", 
                "Full Stack Developer", 
                skills,
                2,
                3,
                12,
                23,
                2L);

            Job job4 = new Job(
                "SDE4", 
                "Full Stack Developer", 
                skills,
                2,
                3,
                12,
                23,
                2L);

            repository.saveAll(List.of(job1, job2, job3, job4));
        };
    }

}

