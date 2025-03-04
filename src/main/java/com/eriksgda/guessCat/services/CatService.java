package com.eriksgda.guessCat.services;

import com.eriksgda.guessCat.model.cats.*;
import com.eriksgda.guessCat.model.game.MatchHistoryResponseDTO;

import java.util.UUID;

public interface CatService {
    LoginResponseDTO create(RegisterAndLoginDTO data);
    LoginResponseDTO authenticate(RegisterAndLoginDTO data);
    DeleteAndUpdateResponseDTO update(UpdateDTO data, UUID playerId);
    DeleteAndUpdateResponseDTO delete(UUID playerId);
    MatchHistoryResponseDTO getUserMatchHistory(UUID playerId);
}
