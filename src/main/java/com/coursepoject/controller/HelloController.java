package com.coursepoject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String defaultURL(){
        return "Welcome";
    }

    @RequestMapping("/hello")
    public String sayHi(){
        return "Hi!";
    }
}
