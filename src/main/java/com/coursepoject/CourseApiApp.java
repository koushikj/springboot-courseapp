package com.coursepoject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EntityScan("com.coursepoject.*")
@SpringBootApplication
@EnableJpaAuditing
public class CourseApiApp {
    public static void main(String[] args) {
        SpringApplication.run(CourseApiApp.class,args);

    }
}
