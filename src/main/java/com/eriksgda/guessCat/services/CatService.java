package com.eriksgda.guessCat.services;

import com.eriksgda.guessCat.model.cats.Cat;
import com.eriksgda.guessCat.model.cats.LoginResponseDTO;
import com.eriksgda.guessCat.model.cats.RegisterAndLoginDTO;

public interface CatService {
    Cat create(RegisterAndLoginDTO data);
    LoginResponseDTO authenticate(RegisterAndLoginDTO data);
}
