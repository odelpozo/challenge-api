package org.wrapper.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.wrapper.exception.ExistingMailException;
import org.wrapper.model.User;
import org.wrapper.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final String VALID_PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(userService, "passwordRegex", VALID_PASSWORD_REGEX);
    }

    @Test
    public void createUser_shouldThrowException_whenPasswordDoesNotMatchRegex() {
        User user = new User();
        user.setPassword("12345");
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        verify(userRepository, never()).save(user);
    }

    @Test
    public void createUser_shouldThrowException_whenEmailAlreadyExists() {
        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        existingUser.setPassword("validPass1");

        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        User newUser = new User();
        newUser.setEmail("test@example.com");
        newUser.setPassword("validPass1");

        assertThrows(ExistingMailException.class, () -> userService.createUser(newUser));

        verify(userRepository, times(1)).findByEmail(newUser.getEmail());
        verify(userRepository, never()).save(newUser);
    }

    @Test
    public void createUser_shouldSaveUser_whenValidInput() {
        User user = new User();
        user.setEmail("new@example.com");
        user.setPassword("validPass1");

        when(userRepository.save(user)).thenReturn(user);


        User savedUser = userService.createUser(user);

        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
    }
}
