package com.NBP.NBP.services;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.NBP.NBP.models.User;
import com.NBP.NBP.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    public void createUser(User user) throws IllegalArgumentException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (!isValidPassword(user.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain a mix of letters and numbers");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    public void updateUser(User user) throws IllegalArgumentException {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            if (!isValidPassword(user.getPassword())) {
                throw new IllegalArgumentException("Password must be at least 8 characters long and contain a mix of letters and numbers");
            }

            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
        }

        if (userRepository.existsByUsernameExcludingId(user.getUsername(), user.getId())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmailExcludingId(user.getEmail(), user.getId())) {
            throw new IllegalArgumentException("Email already exists");
        }

        userRepository.update(user);
    }

    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
        return Pattern.matches(regex, password);
    }
}
