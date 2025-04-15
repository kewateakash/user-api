package com.example.service;

import com.example.config.UserRegistrationProperties;
import com.example.exception.UserCountryNotAllowedException;
import com.example.exception.UserUnderageException;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service

public class UserService {
    private final UserRepository repository;
    private final UserRegistrationProperties properties;

    public UserService(UserRepository repository, UserRegistrationProperties properties) {
        this.repository = repository;
        this.properties = properties;
    }

    public User registerUser(User user, String ref) {
        if (!properties.getAllowedCountry().equalsIgnoreCase(user.getCountry())) {
            throw new UserCountryNotAllowedException("Only users from " + properties.getAllowedCountry() + " are allowed.");
        }
        if (user.getAge() < properties.getMinAge()) {
            throw new UserUnderageException("User must be at least " + properties.getMinAge() + " years old.");
        }
        return repository.save(user);
    }

    public User getUserById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
