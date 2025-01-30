package com.eriksgda.guessCat.services;

import com.eriksgda.guessCat.model.game.Game;
import com.eriksgda.guessCat.model.game.GameMatchDTO;
import com.eriksgda.guessCat.model.game.GameWordResponseDTO;

import java.io.IOException;

public interface GameService {
    GameWordResponseDTO startNewGame() throws IOException;

    Game registerGamePlayed(GameMatchDTO data);
}
