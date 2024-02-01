package dev.harsh.architect.user_service.repositories;

import dev.harsh.architect.user_service.model.Session;
import dev.harsh.architect.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    Optional<Session> findByTokenAndUser(String token, User user);
}
