package com.coursepoject.controller;

import com.coursepoject.model.Course;
import com.coursepoject.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseRestController {
    @Autowired
    CourseRepository courseRepository;

    @GetMapping("/courses")
    public List<Course> getAllCourse() {
        System.out.println(courseRepository.findAll().size());
        return courseRepository.findAllByOrderByIdDesc();
        //return courseRepository.findAll();
    }

    @PostMapping("/courses")
    public Course createCourse(@Valid @RequestBody Course course) {
        return courseRepository.save(course);
    }

    @GetMapping("/courses/{id}")
    public Course getCourse(@PathVariable(value = "id") Long courseId){
        return courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException(Long.toString(courseId)));

    }

    @PutMapping("/courses/{id}")
    public Course updateCourse(@PathVariable(value = "id") Long courseId,
                               @Valid @RequestBody Course courseDetails) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException(Long.toString(courseId)));

        course.setTitle(courseDetails.getTitle());
        course.setContent(courseDetails.getContent());

        Course updatedCourse = courseRepository.save(course);
        return updatedCourse;
    }

    // Delete a Note
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(value = "id") Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException(Long.toString(courseId)));
        courseRepository.delete(course);
        return ResponseEntity.ok().build();
    }

    // Delete a Note
    @DeleteMapping("/courses/last")
    public Course deleteLastCourse() {
        List<Course> courses = courseRepository.findAll();
        Course crs = courses.get(courses.size());
        courseRepository.delete(crs);
        return crs;
    }
}
