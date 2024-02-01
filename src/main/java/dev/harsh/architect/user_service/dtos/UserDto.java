package dev.harsh.architect.user_service.dtos;

import dev.harsh.architect.user_service.model.Role;
import dev.harsh.architect.user_service.model.User;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class UserDto {
    private String email;
    private Set<Role> roles = new HashSet<>();
    public static UserDto fromUser(User user){
        return UserDto.builder()
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();

    }
}
