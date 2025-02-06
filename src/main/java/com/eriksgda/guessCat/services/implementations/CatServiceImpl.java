package com.eriksgda.guessCat.services.implementations;

import com.eriksgda.guessCat.exceptions.InvalidCredentialsException;
import com.eriksgda.guessCat.exceptions.UserDoesNotExistException;
import com.eriksgda.guessCat.exceptions.UsernameAlreadyExistException;
import com.eriksgda.guessCat.infra.security.TokenService;
import com.eriksgda.guessCat.model.cats.*;
import com.eriksgda.guessCat.repositories.CatRepository;
import com.eriksgda.guessCat.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CatServiceImpl implements CatService {

    private static final CatsRoles ROLE = CatsRoles.USER;

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Override
    public Cat create(RegisterAndLoginDTO data) {
        if (this.catRepository.findByUsername(data.username()) != null){
            throw new UsernameAlreadyExistException();
        }

        String encryptedPassword = this.passwordEncoder.encode(data.password());

        Cat newUser = Cat.builder()
                .username(data.username())
                .password(encryptedPassword)
                .role(ROLE)
                .build();

        return this.catRepository.save(newUser);
    }

    @Override
    public LoginResponseDTO authenticate(RegisterAndLoginDTO data) {
        try {
            var UsernameAndPassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
            var auth = this.authenticationManager.authenticate(UsernameAndPassword);

            var token = this.tokenService.generateToken((Cat) auth.getPrincipal());

            return new LoginResponseDTO(token);
        } catch (Exception e) {
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public DeleteAndUpdateResponseDTO update(UpdateDTO data, UUID playerId) {
        Optional<Cat> currentPlayer = this.catRepository.findById(playerId);

        if (currentPlayer.isPresent()) {
            if (this.catRepository.existsByUsername(data.newUsername())) {
                throw new UserDoesNotExistException();
            }
            String encryptedPassword = this.passwordEncoder.encode(data.newPassword());

            Cat playerToUpdate = currentPlayer.get();
            playerToUpdate.setUsername(data.newUsername());
            playerToUpdate.setPassword(encryptedPassword);

            this.catRepository.save(playerToUpdate);

            return new DeleteAndUpdateResponseDTO("User updated successfully.");
        }
        throw new InvalidCredentialsException();
    }

    @Override
    public DeleteAndUpdateResponseDTO delete(UUID playerId) {
        Optional<Cat> currentPlayer = this.catRepository.findById(playerId);

        if (currentPlayer.isPresent()) {
            Cat playerToDelete = currentPlayer.get();
            this.catRepository.delete(playerToDelete);

            return new DeleteAndUpdateResponseDTO("User deleted successfully.");
        }
        throw new InvalidCredentialsException();
    }
}
