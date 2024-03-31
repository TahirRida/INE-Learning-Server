package br.com.fms.authentication.service;

import br.com.fms.authentication.model.Course;
import br.com.fms.authentication.model.User;
import br.com.fms.authentication.repository.CourseRepository;
import br.com.fms.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    //CREATE
    public Course addCourse(Course course, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        course.setCourseOwnerId(username);
        Course createdCourse = courseRepository.save(course);
        user.getOwnedCourses().add(createdCourse);
        userRepository.save(user);
        course.setCourseId(UUID.randomUUID().toString().split("-")[0]);
        return createdCourse;
    }

    //READ ALL
    public List<Course> findAllCourses(){
        return courseRepository.findAll();
    }

    public List<Course> findAllCoursesByUserId(String ownerUsername){
        return courseRepository.findByCourseOwnerId(ownerUsername);
    }

    //READ BY ID
    public Course getCourseById(String courseId){
        return courseRepository.findById(courseId).get();
    }

    //READ BY OWNER
    public  List<Course> getCourseByOwnerId(String courseOwnerId){
        return courseRepository.findByCourseOwnerId(courseOwnerId);
    }

    //UPDATE
    public Course updateCourse(Course newCourse){
        Course oldCourse = courseRepository.findById(newCourse.getCourseId()).get();
        oldCourse.setCourseOwnerId(newCourse.getCourseOwnerId());
        oldCourse.setContent(newCourse.getContent());
        oldCourse.setVideo(newCourse.getVideo());
        oldCourse.setThumbnail(newCourse.getThumbnail());
        oldCourse.setDescription(newCourse.getDescription());
        oldCourse.setCreatedAt(newCourse.getCreatedAt());
        oldCourse.setIsApproved(newCourse.getIsApproved());
        oldCourse.setTitle(newCourse.getTitle());
        return courseRepository.save(oldCourse);
    }

    //DELETE
    public void deleteCourse(String courseId){
        courseRepository.deleteById(courseId);
    }


    public boolean isCourseOwner(String username, String courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            // Check if the course's owner username matches the specified username
            return course.getCourseOwnerId().equals(username);
        }
        return false;
    }
}
