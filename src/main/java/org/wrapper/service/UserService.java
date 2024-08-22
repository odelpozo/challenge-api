package org.wrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wrapper.exception.ExistingMailException;
import org.wrapper.model.User;
import org.wrapper.repository.UserRepository;
import java.util.regex.Pattern;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Value("${password.regex}")
    private String passwordRegex;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {

        if (!Pattern.matches(passwordRegex, user.getPassword())) {
            throw new IllegalArgumentException("La clave no cumple con el formato requerido");
        }

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new ExistingMailException("El correo ya esta registrado");
        }

        String token = UUID.randomUUID().toString();
        user.setToken(token);

        return userRepository.save(user);
    }
}
