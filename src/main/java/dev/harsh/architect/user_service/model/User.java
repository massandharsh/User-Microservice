package dev.harsh.architect.user_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(as = User.class)
@Builder
@Entity
public class User extends BaseModel {
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_roles")
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();
    @JsonIgnore
    private String password;

}
