package br.com.fms.authentication.controller;

import br.com.fms.authentication.model.Course;
import br.com.fms.authentication.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService ;

    //CREATE

    @GetMapping("/pending")
    public List<Course> getPendingCourses() {
        // Use the repository to fetch all courses
        List<Course> allCourses = courseService.findAllCourses();

        // Filter the list of courses to get pending courses
        List<Course> pendingCourses = new ArrayList<>();
        for (Course course : allCourses) {
            if (!course.getIsApproved()) {
                pendingCourses.add(course);
            }
        }

        return pendingCourses;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{courseId}/approve")
    public ResponseEntity<String> approveCourse(@PathVariable String courseId){
        // Get the course by ID
        Course course = courseService.getCourseById(courseId);

        // Check if the course exists
        if (course == null) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        // Approve the course
        course.setIsApproved(true);
        courseService.updateCourse(course);

        return new ResponseEntity<>("Course approved successfully", HttpStatus.OK);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course createCourse(@RequestBody Course course,  @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        return courseService.addCourse(course,username);
    }

    //READ ALL
    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.findAllCourses().stream()
                .filter(Course::getIsApproved) // Filter courses where isApproved is true
                .collect(Collectors.toList());
    }


    @GetMapping("/courses/user")
    public List<Course> getMyCourses(@AuthenticationPrincipal UserDetails userDetails){
        return courseService.findAllCoursesByUserId(userDetails.getUsername());
    }

    //READ COURSE BY ID
    @GetMapping("/{courseId}")
    public Course getSingleCourse(@PathVariable String courseId){
        return courseService.getCourseById(courseId);
    }

    //READ COURSES BY OWNER ID
    @GetMapping("users/{courseOwnerId}")
    public List<Course> getCoursesOfAnOwner(@PathVariable String courseOwnerId){
        return courseService.getCourseByOwnerId(courseOwnerId);
    }

    //UPDATE
    @PutMapping("/{courseId}")
    public Course modifyCourse(@RequestBody Course course){
        return courseService.updateCourse(course);
    }
    //DELETE
    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable String courseId, @AuthenticationPrincipal UserDetails userDetails) {
        // Get the username of the authenticated user
        String username = userDetails.getUsername();

        // Check if the user has admin role
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        // Check if the course belongs to the user or the user is admin
        boolean isCourseOwner = courseService.isCourseOwner(username, courseId);

        if (isCourseOwner || isAdmin) {
            courseService.deleteCourse(courseId);
            return ResponseEntity.status(HttpStatus.OK).body("Sucess");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to perform this operation");
        }
    }}
