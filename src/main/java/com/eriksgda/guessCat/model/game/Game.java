package com.eriksgda.guessCat.model.game;

import com.eriksgda.guessCat.model.cats.Cat;
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

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Cat player;

    @Column(nullable = false)
    private String word;

    @ElementCollection
    @CollectionTable(name = "guesses_table", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "guess")
    private List<String> guesses;

    @CreationTimestamp
    @Column(name = "played_in")
    private LocalDateTime playedIn;
}
