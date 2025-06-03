package com.NBP.NBP.services;

import com.NBP.NBP.models.CustomUser;
import com.NBP.NBP.models.User;
import com.NBP.NBP.models.dtos.CustomUserWithDepartments;
import com.NBP.NBP.models.dtos.PaginatedCustomUserResponseDTO;
import com.NBP.NBP.repositories.CustomUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void updateFullCustomUser(CustomUserWithDepartments customUser) {
        if (customUser.getUserId() == null) {
            Integer userId = findUserIdByCustomUserId(customUser.getId())
                    .orElseThrow(
                            () -> new RuntimeException("User ID not found for CustomUser ID: " + customUser.getId()));
            customUser.setUserId(userId);
        }

        customUserRepository.updateFullCustomUser(customUser);
    }

    public void deleteUser(Integer id) {
        customUserRepository.deleteUser(id);
    }

    public Optional<User> getUserFromUserTable(int userId) {
        return Optional.ofNullable(userService.findById(userId));
    }

    public Optional<Integer> findUserIdByCustomUserId(int customUserId) {
        try {
            Optional<CustomUser> customUser = customUserRepository.findById(customUserId);
            return customUser.map(CustomUser::getUserId);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    public List<CustomUserWithDepartments> getAllUsers() {
        return customUserRepository.findAllWithDepartments();
    }

    public PaginatedCustomUserResponseDTO getAllUsersPaginated(Integer page, Integer size, String sortKey,
            String sortDirection) {
        int p = (page != null && page >= 0) ? page : 0;
        int s = (size != null && size > 0) ? size : 10;
        String direction = sortDirection.equalsIgnoreCase("desc") ? "DESC" : "ASC";

        List<CustomUserWithDepartments> users = customUserRepository.findAllWithDepartmentsPaginated(p, s, sortKey,
                direction);
        long total = customUserRepository.countAllUsers();

        PaginatedCustomUserResponseDTO dto = new PaginatedCustomUserResponseDTO();
        dto.setContent(users);
        dto.setPage(p);
        dto.setSize(s);
        dto.setTotalElements(total);
        dto.setTotalPages((int) Math.ceil((double) total / s));

        System.out.println(dto);
        return dto;
    }

    public Optional<CustomUser> getById(Integer id) {
        return customUserRepository.findById(id);
    }

    public Optional<CustomUserWithDepartments> getByUserIdWithDepartments(Integer userId) {
        return customUserRepository.findByUserIdWithDepartments(userId);
    }

    public Optional<CustomUser> getByUserId(Integer id) {
        return Optional.ofNullable(customUserRepository.findByUserId(id));
    }
}
