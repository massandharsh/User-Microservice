package dev.harsh.architect.user_service.security;

import dev.harsh.architect.user_service.model.User;
import dev.harsh.architect.user_service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomSpringUserDetailService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userService.getUserBasedOnEmail(username);
        if(user == null){
            throw new RuntimeException("!Invalid username!");
        }
        return new CustomSpringUserDetails(user);
    }
}
