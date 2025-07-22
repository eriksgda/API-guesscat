package com.eriksgda.guessCat.services;

import com.eriksgda.guessCat.model.cats.dto.DeleteAndUpdateResponseDTO;
import com.eriksgda.guessCat.model.cats.dto.LoginResponseDTO;
import com.eriksgda.guessCat.model.cats.dto.RegisterAndLoginDTO;
import com.eriksgda.guessCat.model.cats.dto.UpdateDTO;
import com.eriksgda.guessCat.model.game.dto.MatchHistoryResponseDTO;

import java.util.UUID;

public interface CatService {
    LoginResponseDTO create(RegisterAndLoginDTO data);
    LoginResponseDTO authenticate(RegisterAndLoginDTO data);
    DeleteAndUpdateResponseDTO update(UpdateDTO data, UUID playerId);
    DeleteAndUpdateResponseDTO delete(UUID playerId);
    MatchHistoryResponseDTO getUserMatchHistory(UUID playerId);
}
