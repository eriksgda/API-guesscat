package com.eriksgda.guessCat.infra.security;

import com.eriksgda.guessCat.model.cats.Cat;

public interface TokenService {
    String generateToken(Cat data);
    String validateToken(String token);
}
