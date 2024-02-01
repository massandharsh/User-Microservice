package dev.harsh.architect.user_service.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.harsh.architect.user_service.model.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequestDto {
    private String email;
    private String password;
    private String confirmPassword;

    @JsonIgnore
    public User toUser() {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }

}
