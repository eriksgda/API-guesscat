package com.eriksgda.guessCat.controllers;

import com.eriksgda.guessCat.exceptions.InvalidCredentialsException;
import com.eriksgda.guessCat.exceptions.UserDoesNotExistException;
import com.eriksgda.guessCat.exceptions.UsernameAlreadyExistException;
import com.eriksgda.guessCat.model.cats.*;
import com.eriksgda.guessCat.model.game.MatchHistoryResponseDTO;
import com.eriksgda.guessCat.services.CatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("cat")
public class CatController {

    @Autowired
    private CatService catService;

    @PostMapping("auth/login")
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

    @PostMapping("auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterAndLoginDTO data){
        try {
            LoginResponseDTO newUser = this.catService.create(data);
            return ResponseEntity.ok().body(newUser);
        } catch (UsernameAlreadyExistException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }  catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    @PatchMapping("account/update")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateDTO data){
        try {
            Cat currentPlayer = (Cat) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UUID playerId = currentPlayer.getId();

            DeleteAndUpdateResponseDTO response = this.catService.update(data, playerId);
            return ResponseEntity.ok().body(response);
        } catch (InvalidCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        } catch (UserDoesNotExistException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }  catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    @DeleteMapping("account/delete")
    public ResponseEntity<?> delete(){
        try {
            Cat currentPlayer = (Cat) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UUID playerId = currentPlayer.getId();

            DeleteAndUpdateResponseDTO response = this.catService.delete(playerId);
            return ResponseEntity.ok().body(response);
        } catch (InvalidCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        }  catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    @GetMapping("account/history")
    public ResponseEntity<?> getMatchHistory() {
        try {
            Cat currentPlayer = (Cat) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UUID playerId = currentPlayer.getId();

            MatchHistoryResponseDTO response = this.catService.getUserMatchHistory(playerId);
            return ResponseEntity.ok().body(response);
        } catch (InvalidCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        }  catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
}
