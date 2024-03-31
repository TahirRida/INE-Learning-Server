package br.com.fms.authentication.service;

import br.com.fms.authentication.model.Comment;
import br.com.fms.authentication.model.Course;
import br.com.fms.authentication.repository.CommentRepository;
import br.com.fms.authentication.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CourseRepository courseRepository;
    //CREATE

    public Comment addCommentToCourse(String courseId, Comment comment) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            comment.setCourseId(courseId);
            comment.setCommentId(UUID.randomUUID().toString().split("-")[0]);
            course.getComments().add(comment);
            courseRepository.save(course);
            return comment;
        } else {
            throw new IllegalArgumentException("Course not found with id: " + courseId);
        }
    }



    public List<Comment> getAllCommentsForCourse(String courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            return course.getComments();
        } else {
            throw new IllegalArgumentException("Course not found with id: " + courseId);
        }
    }

    //DELETE
    public String deleteComment(String commentId){
        commentRepository.deleteById(commentId);
        return "Comment has been deleted succesfully!";
    }
    public void deleteComment(String courseId, String commentId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            List<Comment> comments = course.getComments();
            if (commentId != null) {
                boolean removed = comments.removeIf(comment -> commentId.equals(comment.getCommentId()));
                if (removed) {
                    course.setComments(comments);
                    courseRepository.save(course);
                } else {
                    throw new IllegalArgumentException("Comment not found with id: " + commentId);
                }
            } else {
                throw new IllegalArgumentException("Comment id cannot be null");
            }
        } else {
            throw new IllegalArgumentException("Course not found with id: " + courseId);
        }
    }

}
