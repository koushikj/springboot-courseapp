package com.coursepoject.controller;

import com.coursepoject.model.Course;
import com.coursepoject.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/webapp")
public class CourseController {
    @Autowired
    CourseRepository courseRepository;

    CourseRestController courseRestController = new CourseRestController();

    @RequestMapping("/")
    public String index(){
        System.out.println("received /...");
        return "course";
    }

    @RequestMapping("/router")
    public String router(){
        System.out.println("received /...");
        return "router";
    }

    @RequestMapping("/getCourses")
    public String getCourses(){
        System.out.println("received /getCourses...");
        List<Course> allCourse = courseRestController.getAllCourse();
        System.out.println("No of courses :"+allCourse.size());
        return "index";
    }

    @RequestMapping("/welcome")
    public String loginMessage(){
        System.out.println("received /welcome...");
        return "welcome";
    }

}
