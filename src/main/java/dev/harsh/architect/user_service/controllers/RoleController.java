package dev.harsh.architect.user_service.controllers;

import dev.harsh.architect.user_service.dtos.CreateRoleRequestDto;
import dev.harsh.architect.user_service.model.Role;
import dev.harsh.architect.user_service.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto requestDto){
        return new ResponseEntity<>(roleService.createRole(requestDto.getName()), HttpStatus.OK);
    }
}
