package com.eriksgda.guessCat.model.game.dto;

import java.time.LocalDateTime;
import java.util.List;

public record GameMatchDTO(
        String word,
        List<String> guesses,
        LocalDateTime playedIn
) {
}
