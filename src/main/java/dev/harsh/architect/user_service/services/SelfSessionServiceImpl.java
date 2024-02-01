package dev.harsh.architect.user_service.services;

import dev.harsh.architect.user_service.enums.SessionStatus;
import dev.harsh.architect.user_service.model.Session;
import dev.harsh.architect.user_service.model.User;
import dev.harsh.architect.user_service.repositories.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@AllArgsConstructor
public class SelfSessionServiceImpl implements SessionService{
    private final SessionRepository sessionRepository;

    @Override
    public Session createSessionInternal(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session getSessionInternal(String token, User user) {
        return sessionRepository.findByTokenAndUser(token,user).orElse(null);
    }

    @Override
    public SessionStatus getSessionStatusInternal(String token, User user) {
        Session session = getSessionInternal(token,user);
        if(session == null){
            return SessionStatus.INVALID;
        }
        return session.getSessionStatus();
    }
}
