package dev.harsh.architect.user_service.services;

import dev.harsh.architect.user_service.model.Role;
import dev.harsh.architect.user_service.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class SelfRoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;
    @Override
    public Role createRole(String name) {
        Role role = new Role(name);
        return roleRepository.save(role);
    }

    @Override
    public Set<Role> getRolesInternal() {
        return new HashSet<>(roleRepository.findAll());
    }


}
