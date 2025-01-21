package com.eriksgda.guessCat.services.implementations;

import com.eriksgda.guessCat.exceptions.InvalidCredentialsException;
import com.eriksgda.guessCat.exceptions.UsernameAlreadyExistException;
import com.eriksgda.guessCat.infra.security.TokenService;
import com.eriksgda.guessCat.model.cats.Cat;
import com.eriksgda.guessCat.model.cats.CatsRoles;
import com.eriksgda.guessCat.model.cats.LoginResponseDTO;
import com.eriksgda.guessCat.model.cats.RegisterAndLoginDTO;
import com.eriksgda.guessCat.repositories.CatRepository;
import com.eriksgda.guessCat.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
