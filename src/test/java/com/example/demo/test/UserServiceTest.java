package com.example.demo.test;

import com.example.config.UserRegistrationProperties;
import com.example.exception.UserCountryNotAllowedException;
import com.example.exception.UserUnderageException;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository repository;
    private UserRegistrationProperties properties;
    private UserService userService;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        properties = mock(UserRegistrationProperties.class);
        userService = new UserService(repository, properties);

        // Default config values
        when(properties.getAllowedCountry()).thenReturn("France");
        when(properties.getMinAge()).thenReturn(18);
    }

    @Test
    void testRegisterUser_successful() {
        User user = new User();
        user.setName("Alice");
        user.setAge(25);
        user.setCountry("France");

        when(repository.save(user)).thenReturn(user);

        User result = userService.registerUser(user, "ref123");

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        verify(repository, times(1)).save(user);
    }

    @Test
    void testRegisterUser_invalidCountry_throwsException() {
        User user = new User();
        user.setName("Bob");
        user.setAge(30);
        user.setCountry("Germany"); // Not allowed

        Exception exception = assertThrows(UserCountryNotAllowedException.class,
                () -> userService.registerUser(user, "ref123"));

        assertTrue(exception.getMessage().contains("Only users from"));
        verify(repository, never()).save(any());
    }

    @Test
    void testRegisterUser_underAge_throwsException() {
        User user = new User();
        user.setName("Charlie");
        user.setAge(17);
        user.setCountry("France");

        Exception exception = assertThrows(UserUnderageException.class,
                () -> userService.registerUser(user, "ref123"));

        assertTrue(exception.getMessage().contains("User must be at least"));
        verify(repository, never()).save(any());
    }

    @Test
    void testGetUserById_found() {
        User user = new User();
        user.setId("123");
        user.setName("David");

        when(repository.findById("123")).thenReturn(Optional.of(user));

        User result = userService.getUserById("123");

        assertEquals("David", result.getName());
        verify(repository, times(1)).findById("123");
    }

    @Test
    void testGetUserById_notFound_throwsException() {
        when(repository.findById("404")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> userService.getUserById("404"));

        assertEquals("User not found", exception.getMessage());
        verify(repository).findById("404");
    }
}


