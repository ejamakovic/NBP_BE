package com.NBP.NBP.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.NBP.NBP.models.CustomUser;
import com.NBP.NBP.models.User;

import com.NBP.NBP.repositories.CustomUserRepository;
import com.NBP.NBP.security.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final CustomUserRepository customUserRepository;

    public CustomUserDetailsService(UserService userService, CustomUserRepository customUserRepository) {
        this.userService = userService;
        this.customUserRepository = customUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        CustomUser customUser = customUserRepository.findByUserId(user.getId());

        user.setCustomUserId(customUser != null ? customUser.getId() : null);

        return new CustomUserDetails(user);
    }

}
