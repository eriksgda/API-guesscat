package com.eriksgda.guessCat.model.game;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GameResponseDTO(
        UUID id,
        String word,
        List<String> guesses,
        LocalDateTime playedIn
) {
    public static GameResponseDTO fromEntity(Game game) {
        return new GameResponseDTO(
                game.getId(),
                game.getWord(),
                game.getGuesses(),
                game.getPlayedIn()
        );
    }
}
