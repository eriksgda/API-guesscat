package com.eriksgda.guessCat.model.game.dto;

import java.util.List;

public record MatchHistoryResponseDTO(
        String username,
        List<GameResponseDTO> matchHistory
) {
}
