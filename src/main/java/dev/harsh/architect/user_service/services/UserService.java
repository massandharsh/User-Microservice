package dev.harsh.architect.user_service.services;

import dev.harsh.architect.user_service.dtos.UserDto;
import dev.harsh.architect.user_service.model.Role;
import dev.harsh.architect.user_service.model.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface UserService {
    UserDto getUserDetails(String id);

    UserDto setUserRoles(String userId, Set<Role> roles);

    User getUserBasedOnEmail(String email);
}
