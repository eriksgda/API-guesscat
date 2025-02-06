package com.eriksgda.guessCat.services;

import com.eriksgda.guessCat.model.cats.*;

import java.util.UUID;

public interface CatService {
    Cat create(RegisterAndLoginDTO data);
    LoginResponseDTO authenticate(RegisterAndLoginDTO data);
    DeleteAndUpdateResponseDTO update(UpdateDTO data, UUID playerId);
    DeleteAndUpdateResponseDTO delete(UUID playerId);
}
