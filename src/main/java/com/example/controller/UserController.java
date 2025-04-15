package com.example.controller;

import com.example.config.UserRegistrationProperties;
import com.example.model.User;
import com.example.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")

@Validated
public class UserController {

    private final UserService userService;
    private final UserRegistrationProperties userRegistrationProperties;

    public UserController(UserService userService, UserRegistrationProperties userRegistrationProperties) {
        this.userService = userService;
        this.userRegistrationProperties = userRegistrationProperties;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user,
                                          @RequestParam(defaultValue = "no-ref") String ref) {
        if (!userRegistrationProperties.getAllowedCountry().equalsIgnoreCase(user.getCountry())
                && user.getAge() > userRegistrationProperties.getMinAge()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Only users from France and age 18 or above can register.");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registerUser(user, ref));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

}

