package com.eriksgda.guessCat.services;

import com.eriksgda.guessCat.model.cats.dto.*;
import com.eriksgda.guessCat.model.game.dto.MatchHistoryResponseDTO;

import java.util.UUID;

public interface CatService {
    LoginResponseDTO create(RegisterAndLoginDTO data);
    LoginResponseDTO authenticate(RegisterAndLoginDTO data);
    LoginResponseDTO refreshToken(RefreshTokenDTO data);
    DeleteAndUpdateResponseDTO update(UpdateDTO data, UUID playerId);
    DeleteAndUpdateResponseDTO delete(UUID playerId);
    MatchHistoryResponseDTO getUserMatchHistory(UUID playerId);
}
