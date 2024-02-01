package dev.harsh.architect.user_service.services;

import dev.harsh.architect.user_service.model.Role;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RoleService {
    Role createRole(String name);
    Set<Role> getRolesInternal();
}
