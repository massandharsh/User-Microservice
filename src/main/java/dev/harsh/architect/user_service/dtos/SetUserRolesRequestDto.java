package dev.harsh.architect.user_service.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.harsh.architect.user_service.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class SetUserRolesRequestDto {
    private Set<String> roles = new HashSet<>();
    @JsonIgnore
    public static Role toRole(String name){
        return new Role(name);
    }
    @JsonIgnore
    public Set<Role> toRoles(){
        return roles.stream()
                .map(SetUserRolesRequestDto::toRole)
                .collect(Collectors.toSet());
    }
}

