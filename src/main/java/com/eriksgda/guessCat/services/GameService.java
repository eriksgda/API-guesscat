package com.eriksgda.guessCat.services;

import com.eriksgda.guessCat.model.historic.GameWordResponseDTO;

import java.io.IOException;

public interface GameService {
    GameWordResponseDTO startNewGame() throws IOException;
}
