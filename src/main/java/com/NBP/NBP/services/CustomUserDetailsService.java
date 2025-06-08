package com.NBP.NBP.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.NBP.NBP.models.User;

import com.NBP.NBP.repositories.CustomUserRepository;
import com.NBP.NBP.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomUserRepository customUserRepository;

    public CustomUserDetailsService(UserRepository userRepository, CustomUserRepository customUserRepository) {
        this.userRepository = userRepository;
        this.customUserRepository = customUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var nbpUser = userRepository.findByEmail(username);

        if (nbpUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        var customUser = customUserRepository.findByUserId(nbpUser.getId());
        if (customUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        var user = new User();
        user.setId(nbpUser.getId());
        user.setEmail(nbpUser.getEmail());
        user.setFirstName(nbpUser.getFirstName());
        user.setLastName(nbpUser.getLastName());
        user.setUsername(nbpUser.getEmail());
        user.setPassword(nbpUser.getPassword());
        user.setRoleId(nbpUser.getRoleId());
        user.setCustomUserId(customUser != null ? customUser.getId() : null);

        return user;
    }
}
