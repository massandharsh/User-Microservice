package dev.harsh.architect.user_service.dtos;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponseDto {
    private UserDto userDto;
    private String token;
}
