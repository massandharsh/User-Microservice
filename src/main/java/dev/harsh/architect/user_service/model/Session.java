package dev.harsh.architect.user_service.model;

import dev.harsh.architect.user_service.enums.SessionStatus;
import dev.harsh.architect.user_service.listeners.SessionEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@EntityListeners(SessionEntityListener.class)
public class Session extends BaseModel{
    @Column(columnDefinition = "varchar(511)")
    private String token;
    private LocalDateTime expiringAt;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;
}
