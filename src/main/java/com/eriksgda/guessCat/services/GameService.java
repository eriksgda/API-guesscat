package com.eriksgda.guessCat.services;

import com.eriksgda.guessCat.model.game.Game;
import com.eriksgda.guessCat.model.game.GameMatchDTO;
import com.eriksgda.guessCat.model.game.GameResponseDTO;
import com.eriksgda.guessCat.model.game.GameWordResponseDTO;

import java.io.IOException;
import java.util.UUID;

public interface GameService {
    GameWordResponseDTO startNewGame() throws IOException;

    GameResponseDTO registerGamePlayed(GameMatchDTO data, UUID playerId);
}
