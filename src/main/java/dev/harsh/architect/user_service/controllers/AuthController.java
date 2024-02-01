package dev.harsh.architect.user_service.controllers;

import dev.harsh.architect.user_service.dtos.*;
import dev.harsh.architect.user_service.enums.SessionStatus;
import dev.harsh.architect.user_service.model.Session;
import dev.harsh.architect.user_service.services.AuthService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto loginResponseDto = authService.loginAndGetToken(loginRequestDto.toUser());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE,"auth-token:" + loginResponseDto.getToken());
        return new ResponseEntity<>(loginResponseDto.getUserDto(),httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto){
        if(!signupRequestDto.getPassword().equals(signupRequestDto.getConfirmPassword())){
            throw new RuntimeException("Password does not match");
        }
        return new ResponseEntity<>(authService.signUp(signupRequestDto.toUser()),HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto){
        return new ResponseEntity<>(authService.logout(logoutRequestDto),HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(ValidateTokenRequestDto requestDto){
        return new ResponseEntity<>(authService.validate(requestDto),HttpStatus.OK);
    }
}
