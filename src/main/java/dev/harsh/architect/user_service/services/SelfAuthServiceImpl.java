package dev.harsh.architect.user_service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.harsh.architect.user_service.dtos.LoginResponseDto;
import dev.harsh.architect.user_service.dtos.LogoutRequestDto;
import dev.harsh.architect.user_service.dtos.UserDto;
import dev.harsh.architect.user_service.dtos.ValidateTokenRequestDto;
import dev.harsh.architect.user_service.enums.SessionStatus;
import dev.harsh.architect.user_service.model.Role;
import dev.harsh.architect.user_service.model.Session;
import dev.harsh.architect.user_service.model.User;
import dev.harsh.architect.user_service.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service("selfAuthServiceImpl")
@Primary
@AllArgsConstructor
@Slf4j
public class SelfAuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public LoginResponseDto loginAndGetToken(User user) {
        //Get and validate the user
        User dbUser = getUserInternalBasedOnEmail(user.getEmail());
        if(dbUser == null){
            throw new RuntimeException("User does not exist");
        }
        if(!bCryptPasswordEncoder.matches(user.getPassword(),dbUser.getPassword()))
        {
            throw new RuntimeException("!!Username or password is incorrect!!");
        }
        //If user exist generate the token
//        String secret = RandomStringUtils.randomAlphanumeric(30);

        String jwtToken =  getJwtToken(dbUser);
        System.out.println(jwtToken);

        Session session = Session.builder()
                .token(jwtToken)
                .user(dbUser)
                .sessionStatus(SessionStatus.ACTIVE)
                .build();
        session = sessionService.createSessionInternal(session);
        return LoginResponseDto.builder()
                .userDto(UserDto.fromUser(dbUser))
                .token(session.getToken())
                .build();
    }

    private String getJwtToken(User user) {
        MacAlgorithm alg = Jwts.SIG.HS256; //or HS384 or HS256
        SecretKey key = alg.key().build();
        Map<String,Object> jsonForJwt = new HashMap<>();
        String [] roles = user.getRoles()
                                  .stream()
                                  .map(Role::getName)
                                          .toArray(String[]::new);
        jsonForJwt.put("email", user.getEmail());
        jsonForJwt.putIfAbsent("roles", roles);
        jsonForJwt.put("createdAt", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        jsonForJwt.put("expiryAt",LocalDateTime.now().plusHours(3).toEpochSecond(ZoneOffset.UTC));
// Create the compact JWS:
        return Jwts.builder().claims(jsonForJwt).signWith(key, alg).compact();
    }

    @Override
    public UserDto signUp(User user) {
        User dbUser = getUserInternalBasedOnEmail(user.getEmail());
        if(dbUser != null){
            throw new RuntimeException("User already exists");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User persistedUser = userRepository.save(user);
        return UserDto.fromUser(persistedUser);
    }

    @Override
    public Void logout(LogoutRequestDto logoutRequestDto) {
        //Fetch user based on the request
        Optional<User> user = userRepository.findById(logoutRequestDto.getUserId());
        if(user.isEmpty()){
            throw new RuntimeException("User does not exist");
        }
        Session session = sessionService.getSessionInternal(logoutRequestDto.getToken(),user.get());
        if(session == null){
            throw new RuntimeException("Invalid session!");
        }
        session.setSessionStatus(SessionStatus.ENDED);
        return null;
    }

    private <T> T getJwtPayloadData(Jws<Claims> claimsJwsString,String key,Class<T> tClass){
        return tClass.cast(claimsJwsString.getPayload().get(key));
    }

    @Override
    public SessionStatus validate(ValidateTokenRequestDto requestDto) {
        Optional<User> user = userRepository.findById(requestDto.getUserId());
        if(user.isEmpty()){
            return SessionStatus.INVALID;
        }
        SessionStatus sessionStatus = sessionService.getSessionStatusInternal(requestDto.getToken(),user.get());
        if(sessionStatus != SessionStatus.ACTIVE){
            return SessionStatus.ENDED;
        }

        Jws<Claims> claimsJws = Jwts.parser()
                .build()
                .parseSignedClaims(requestDto.getToken());

        String email = getJwtPayloadData(claimsJws,"email",String.class);
        String [] roles = getJwtPayloadData(claimsJws,"roles",String[].class);
        Long createdAt = getJwtPayloadData(claimsJws,"createdAt",Long.class);
        Long expiryAt = getJwtPayloadData(claimsJws,"expiryAt",Long.class);

        if(expiryAt <= LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)){
            return SessionStatus.ENDED;
        }



        return sessionStatus;
    }

    private User getUserInternalBasedOnEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }
}
