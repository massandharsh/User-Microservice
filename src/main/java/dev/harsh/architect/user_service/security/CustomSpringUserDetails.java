package dev.harsh.architect.user_service.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.harsh.architect.user_service.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonDeserialize(as = CustomSpringUserDetails.class)
public class CustomSpringUserDetails implements UserDetails, Serializable {
    private User user;
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().toList();
    }

    @Override
    @JsonIgnore

    public String getPassword() {
        return user.getPassword();
    }

    @Override
    @JsonIgnore

    public String getUsername() {
        return user.getEmail();
    }

    @Override
    @JsonIgnore

    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore

    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore

    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore

    public boolean isEnabled() {
        return true;
    }
}
