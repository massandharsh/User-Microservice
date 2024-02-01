package dev.harsh.architect.user_service.services;

import dev.harsh.architect.user_service.dtos.UserDto;
import dev.harsh.architect.user_service.model.Role;
import dev.harsh.architect.user_service.model.User;
import dev.harsh.architect.user_service.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class SelfUserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleService roleService;
    @Override
    public UserDto getUserDetails(String id) {
        Optional<User> userOptional = userRepository.findById(UUID.fromString(id));
        if(userOptional.isEmpty()){
            throw new RuntimeException("User does not exist");
        }
        return UserDto.fromUser(userOptional.get());
    }

    @Override
    public UserDto setUserRoles(String userId, Set<Role> roles) {
        Set<Role> allRoles = roleService.getRolesInternal();
        boolean validRoles = allRoles.containsAll(roles);
        if(!validRoles){
            throw new RuntimeException("Some of the provided roles are invalid");
        }
        Optional<User> userOptional = userRepository.findById(UUID.fromString(userId));
        if(userOptional.isEmpty()){
            throw new RuntimeException("Invalid User!!");
        }
        Set<Role> assignRoles = allRoles.stream().filter(roles::contains).collect(Collectors.toSet());
        User user = userOptional.get();
        user.setRoles(assignRoles);
        return UserDto.fromUser(userRepository.save(user));
    }

    @Override
    public User getUserBasedOnEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }


}
