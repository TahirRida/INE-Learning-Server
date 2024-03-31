package br.com.fms.authentication.model;

import java.time.Instant;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class Comment {
    @Id
    private String commentId;
    private String courseId;
    private String commentOwnerId;
    private String contentComment;
    private Instant createdAtComment;
}
