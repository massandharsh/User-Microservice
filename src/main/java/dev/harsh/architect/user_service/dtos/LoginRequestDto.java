package dev.harsh.architect.user_service.dtos;

import dev.harsh.architect.user_service.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    private String email;
    private String password;

    public User toUser(){
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }
}
