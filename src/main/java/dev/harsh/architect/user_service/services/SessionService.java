package dev.harsh.architect.user_service.services;

import dev.harsh.architect.user_service.enums.SessionStatus;
import dev.harsh.architect.user_service.model.Session;
import dev.harsh.architect.user_service.model.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Primary
public interface SessionService {
     Session createSessionInternal(Session session);
     Session getSessionInternal(String token, User user);
     SessionStatus getSessionStatusInternal(String token, User user);
}
