package com.eriksgda.guessCat.model.game;

import com.eriksgda.guessCat.model.cats.Cat;
import com.eriksgda.guessCat.model.game.dto.GameStatus;
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
@Table(name = "games")
@Entity(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Cat player;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private Long points;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus status;

    @Column(nullable = false)
    private Integer tries;

    @ElementCollection
    @CollectionTable(name = "guesses_table", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "guess", nullable = false)
    private List<String> guesses;

    @CreationTimestamp
    @Column(name = "played_in", updatable = false)
    private LocalDateTime playedIn;
}
