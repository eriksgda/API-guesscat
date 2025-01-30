package com.eriksgda.guessCat.model.game;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GameMatchDTO(
        UUID playerId,
        String word,
        List<String> guesses,
        LocalDateTime playedIn
) {
}
