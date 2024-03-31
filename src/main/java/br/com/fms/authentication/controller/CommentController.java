package br.com.fms.authentication.controller;
import br.com.fms.authentication.model.Comment;
import br.com.fms.authentication.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/courses/{courseId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //CREATE

    @PostMapping
    public ResponseEntity<Comment> addCommentToCourse(@PathVariable String courseId, @RequestBody Comment comment) {
        Comment savedComment = commentService.addCommentToCourse(courseId, comment);
        return ResponseEntity.ok(savedComment);
    }

    //READ ALL

    @GetMapping
    public ResponseEntity<List<Comment>> getAllCommentsForCourse(@PathVariable String courseId) {
        List<Comment> comments = commentService.getAllCommentsForCourse(courseId);
        return ResponseEntity.ok(comments);
    }

    //UPDATE

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String courseId, @PathVariable String commentId) {
        commentService.deleteComment(courseId, commentId);
        return ResponseEntity.noContent().build();
    }
}