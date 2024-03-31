package br.com.fms.authentication.repository;


import br.com.fms.authentication.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface CourseRepository extends MongoRepository<Course, String> {

    List<Course> findByCourseOwnerId(String courseOwnerId);


}
