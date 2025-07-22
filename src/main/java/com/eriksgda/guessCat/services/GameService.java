package com.eriksgda.guessCat.services;

import com.eriksgda.guessCat.model.game.dto.GameMatchDTO;
import com.eriksgda.guessCat.model.game.dto.GameResponseDTO;
import com.eriksgda.guessCat.model.game.dto.GameWordResponseDTO;

import java.io.IOException;
import java.util.UUID;

public interface GameService {
    GameWordResponseDTO startNewGame() throws IOException;

    GameResponseDTO registerGamePlayed(GameMatchDTO data, UUID playerId);
}
