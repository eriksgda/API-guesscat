package com.eriksgda.guessCat.infra.security.implementation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.eriksgda.guessCat.infra.security.TokenService;
import com.eriksgda.guessCat.model.cats.Cat;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(this.secret);
    }

    @Override
    public String generateToken(Cat data) {
        try {
            return JWT.create()
                    .withIssuer("guess-cat")
                    .withSubject(data.getUsername())
                    .withClaim("id", data.getId().toString())
                    .withClaim("username", data.getUsername())
                    .withExpiresAt(generateExpiredDateForToken(2))
                    .sign(this.algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generate token.", exception);
        }
    }

    @Override
    public String validateToken(String token) {
        try {
            return JWT.require(this.algorithm)
                    .withIssuer("guess-cat")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    @Override
    public String generateRefreshToken(Cat data) {
        try {
            return JWT.create()
                    .withIssuer("guess-cat")
                    .withSubject(data.getUsername())
                    .withClaim("type", "refresh")
                    .withExpiresAt(generateExpiredDateForToken(24 * 7))
                    .sign(this.algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generate refresh token.", exception);
        }
    }

    @Override
    public String getUsernameFromRefreshToken(String token) {
        try {
            return JWT.require(algorithm)
                    .withIssuer("guess-cat")
                    .withClaim("type", "refresh")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    @Override
    public boolean isRefreshTokenExpired(String token) {
        return this.getUsernameFromRefreshToken(token) != null;
    }

    private Instant generateExpiredDateForToken(int hoursToExpireToken){
        return LocalDateTime.now(ZoneOffset.UTC).plusHours(hoursToExpireToken).toInstant(ZoneOffset.UTC);
    }
}
