package com.eriksgda.guessCat.model.cats;

import com.eriksgda.guessCat.model.game.Game;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cats")
@Entity(name = "cats")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 12)
    private String username;

    @Column(nullable = false, length = 80)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CatsRoles role;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Game> historic;

    @Builder.Default
    @Column(name="total_points" ,nullable = false)
    private Long totalPoints = 0L; //default

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
