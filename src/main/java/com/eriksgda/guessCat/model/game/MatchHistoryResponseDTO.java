package com.eriksgda.guessCat.model.game;

import java.util.List;

public record MatchHistoryResponseDTO(
        String username,
        List<GameResponseDTO> matchHistory
) {
}
