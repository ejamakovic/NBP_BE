package com.NBP.NBP.services;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.time.Year;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.NBP.NBP.models.CustomUser;
import com.NBP.NBP.models.User;
import com.NBP.NBP.models.dtos.UserRegistrationDTO;
import com.NBP.NBP.repositories.CustomUserRepository;
import com.NBP.NBP.repositories.DepartmentRepository;
import com.NBP.NBP.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CustomUserRepository customUserRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
            CustomUserRepository customUserRepository,
            DepartmentRepository departmentRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customUserRepository = customUserRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        User user = userRepository.findById(id);
        if (user != null) {
            return user;
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public Integer findUserIdById(int id) {
        User user = userRepository.findById(id);
        if (user != null) {
            return user.getId();
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }


    public void saveUser(User user) throws IllegalArgumentException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (!isValidPassword(user.getPassword())) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long and contain a mix of letters and numbers");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    @Transactional
    public void saveUserAndCustomUser(UserRegistrationDTO dto) {
        User user = dto.getUser();
        List<Integer> departmentIds = dto.getDepartmentIds();

        if (departmentIds == null || departmentIds.size() == 0) {
            throw new IllegalArgumentException("Department IDs must be specified");
        }

        if (!departmentRepository.departmentIdsExist(departmentIds)) {
            throw new IllegalArgumentException("One or more provided departments does not exist");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (!isValidPassword(user.getPassword())) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long and contain a mix of letters and numbers");
        }

        try {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);

            user = userRepository.save(user);

            CustomUser customUser = new CustomUser();
            customUser.setUserId(user.getId());
            customUser.setYear(Year.now().getValue());

            int customUserId = customUserRepository.save(customUser);
            customUser.setId(customUserId);

            customUserRepository.saveCustomUserDepartments(customUserId, departmentIds);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user and custom user: " + e.getMessage(), e);
        }
    }

    public void updateUser(User user) throws IllegalArgumentException {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            if (!isValidPassword(user.getPassword())) {
                throw new IllegalArgumentException(
                        "Password must be at least 8 characters long and contain a mix of letters and numbers");
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

    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
        return Pattern.matches(regex, password);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }
}
