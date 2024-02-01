package dev.harsh.architect.user_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = false)
@JsonDeserialize(as = Role.class)
public class Role extends BaseModel implements GrantedAuthority {
    private String name;
    @Override
    @JsonIgnore
    public String getAuthority() {
        return this.name;
    }
}
