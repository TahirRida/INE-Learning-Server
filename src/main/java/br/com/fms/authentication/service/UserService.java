package br.com.fms.authentication.service;

import br.com.fms.authentication.model.Course;
import br.com.fms.authentication.model.User;
import br.com.fms.authentication.repository.CourseRepository;
import br.com.fms.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;
@CrossOrigin

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private CourseRepository courseRepository;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> updateUser(String username, User newUser) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            // Update only specified attributes
            if (newUser.getUsername() != null) {
                existingUser.setUsername(newUser.getUsername());
            }
            if (newUser.getEmail() != null) {
                existingUser.setEmail(newUser.getEmail());
            }
            if (newUser.getPassword() != null) {
                existingUser.setPassword(encoder.encode(newUser.getPassword()));
            }
            // Retain existing roles if new roles are not specified
            if (newUser.getRoles() == null || newUser.getRoles().isEmpty()) {
                newUser.setRoles(existingUser.getRoles());
            }
            // Update owned courses if specified, or retain existing owned courses
            if (newUser.getOwnedCourses() != null && !newUser.getOwnedCourses().isEmpty()) {
                existingUser.setOwnedCourses(newUser.getOwnedCourses());
            } else {
                newUser.setOwnedCourses(existingUser.getOwnedCourses());
            }

            // Update courseOwnerId for all courses owned by the user
            List<Course> userCourses = courseRepository.findByCourseOwnerId(username);
            userCourses.forEach(course -> course.setCourseOwnerId(newUser.getUsername()));
            courseRepository.saveAll(userCourses);

            return Optional.of(userRepository.save(existingUser));
        } else {
            // Handle case where user with given username is not found
            return Optional.empty();
        }
    }

    public void updateProfilePicture(String profilePicture, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setProfilePicture(profilePicture);
            userRepository.save(existingUser);
        }
    }
}

