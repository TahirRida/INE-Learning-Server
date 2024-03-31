package br.com.fms.authentication.repository;

import br.com.fms.authentication.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findCommentsByCourseId(String courseId);
}
