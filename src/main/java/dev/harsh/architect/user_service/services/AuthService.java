package dev.harsh.architect.user_service.services;

import dev.harsh.architect.user_service.dtos.*;
import dev.harsh.architect.user_service.enums.SessionStatus;
import dev.harsh.architect.user_service.model.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AuthService {

    LoginResponseDto loginAndGetToken(User user);

    UserDto signUp(User user);

    Void logout(LogoutRequestDto logoutRequestDto);

    SessionStatus validate(ValidateTokenRequestDto requestDto);
}
