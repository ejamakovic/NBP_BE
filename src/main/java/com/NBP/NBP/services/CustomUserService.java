package com.NBP.NBP.services;

import com.NBP.NBP.models.CustomUser;
import com.NBP.NBP.repositories.CustomUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserService {

    private final CustomUserRepository repository;

    public CustomUserService(CustomUserRepository repository) {
        this.repository = repository;
    }

    public List<CustomUser> getAllUsers() {
        return repository.findAll();
    }

    public CustomUser getUserById(int id) {
        return repository.findById(id);
    }

    public void createUser(CustomUser user) {
        repository.save(user);
    }

    public void updateUser(CustomUser user) {
        repository.update(user);
    }

    public void deleteUser(int id) {
        repository.delete(id);
    }

    public CustomUser login(String username, String password) {
        return repository.findByUsernameAndPassword(username, password);
    }
}
