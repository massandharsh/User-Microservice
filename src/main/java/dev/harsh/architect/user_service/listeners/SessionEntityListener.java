package dev.harsh.architect.user_service.listeners;

import dev.harsh.architect.user_service.model.Session;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
@Component
public class SessionEntityListener {
    @PrePersist
    @PreUpdate
    public void setTokenExpiry(Session session){
        session.setExpiringAt(LocalDateTime.now().plusHours(1));
    }
}
