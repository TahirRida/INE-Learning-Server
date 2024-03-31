package br.com.fms.authentication.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;

    //    @NotBlank
//    @Size(max = 20)
    private String username;

    //    @NotBlank
//    @Size(max = 50)
//    @Email
    private String email;

    //    @NotBlank
//    @Size(max = 120)
    private String password;
    private String profilePicture;

    @DBRef
    private Set<Role> roles = new HashSet<>();
    @DBRef
    private List<Course> ownedCourses = new ArrayList<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
