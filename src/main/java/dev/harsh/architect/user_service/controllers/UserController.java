package dev.harsh.architect.user_service.controllers;

import dev.harsh.architect.user_service.dtos.SetUserRolesRequestDto;
import dev.harsh.architect.user_service.dtos.UserDto;
import dev.harsh.architect.user_service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") String id){
        return new ResponseEntity<>(userService.getUserDetails(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
    private ResponseEntity<UserDto> setUserRoles(@PathVariable("id") String userId,@RequestBody SetUserRolesRequestDto setUserRolesRequestDto){
        return new ResponseEntity<>(userService.setUserRoles(userId,setUserRolesRequestDto.toRoles()),HttpStatus.OK);
    }
}
