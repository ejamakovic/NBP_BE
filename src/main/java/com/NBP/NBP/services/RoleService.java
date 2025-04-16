package com.NBP.NBP.services;

import com.NBP.NBP.models.Role;
import com.NBP.NBP.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(int id) {
        return roleRepository.findById(id);
    }

    public int saveRole(Role role) {
        return roleRepository.save(role);
    }

    public int updateRole(Role role) {
        return roleRepository.update(role);
    }

    public int deleteRole(int id) {
        return roleRepository.delete(id);
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}