package br.com.fms.authentication.controller;

import br.com.fms.authentication.model.User;
import br.com.fms.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> userOptional = userService.getUserByUsername(username);
        return userOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user) {
        Optional<User> optionalUser = userService.updateUser(username, user);
        return optionalUser.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{username}/picture")
    public ResponseEntity<String> updateProfilePicture(
            @PathVariable("username") String username,
            @RequestBody User user) {
        userService.updateProfilePicture(user.getProfilePicture(), username);
        return ResponseEntity.ok("Profile picture updated successfully.");
    }
}

