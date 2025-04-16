package com.NBP.NBP.services;

import com.NBP.NBP.models.CustomUser;
import com.NBP.NBP.models.User;
import com.NBP.NBP.repositories.CustomUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserService {

    private final CustomUserRepository customUserRepository;
    private final UserService userService;

    public CustomUserService(CustomUserRepository customUserRepository, UserService userService) {
        this.customUserRepository = customUserRepository;
        this.userService = userService;
    }

    public void saveUser(CustomUser customUser) throws IllegalArgumentException {
        Optional<User> user = getUserFromUserTable(customUser.getUserId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User doesn't exist");
        }

        customUserRepository.save(customUser);
    }

    public void updateUser(CustomUser customUser) throws IllegalArgumentException {
        Optional<User> user = getUserFromUserTable(customUser.getUserId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User doesn't exist");
        }

        customUserRepository.update(customUser);
    }

    public void deleteUser(int id) {
        customUserRepository.delete(id);
    }

    public Optional<User> getUserFromUserTable(int userId) {
        return Optional.ofNullable(userService.findById(userId));
    }

    public List<CustomUser> getAllUsers() {
        return customUserRepository.findAll();
    }

    public Optional<CustomUser> getById(int id) {
        return customUserRepository.findById(id);
    }

    public Optional<CustomUser> getByUserId(int id) {
        return Optional.ofNullable(customUserRepository.findByUserId(id));
    }
}
