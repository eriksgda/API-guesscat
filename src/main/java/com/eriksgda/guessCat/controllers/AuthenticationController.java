package com.eriksgda.guessCat.controllers;

import com.eriksgda.guessCat.exceptions.InvalidCredentialsException;
import com.eriksgda.guessCat.exceptions.UsernameAlreadyExistException;
import com.eriksgda.guessCat.model.cats.Cat;
import com.eriksgda.guessCat.model.cats.LoginResponseDTO;
import com.eriksgda.guessCat.model.cats.RegisterAndLoginDTO;
import com.eriksgda.guessCat.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private CatService catService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RegisterAndLoginDTO data){
        try {
            LoginResponseDTO response = this.catService.authenticate(data);
            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterAndLoginDTO data){
        try {
            Cat newUser = this.catService.create(data);
            return ResponseEntity.ok().body(newUser);
        } catch (UsernameAlreadyExistException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }  catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
}
