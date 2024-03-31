package br.com.fms.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    private String courseId;
    private String courseOwnerId;
    private String content;
    private String title;
    private String description;
    private String video;
    private String category;
    private String thumbnail;
    private Instant createdAt;
    private Boolean isApproved=false;
    List<Comment> comments = new ArrayList<>();

}
