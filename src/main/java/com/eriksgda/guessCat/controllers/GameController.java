package com.eriksgda.guessCat.controllers;

import com.eriksgda.guessCat.exceptions.FileIsEmptyException;
import com.eriksgda.guessCat.exceptions.UserDoesNotExistException;
import com.eriksgda.guessCat.model.cats.Cat;
import com.eriksgda.guessCat.model.game.dto.GameMatchDTO;
import com.eriksgda.guessCat.model.game.dto.GameResponseDTO;
import com.eriksgda.guessCat.model.game.dto.GameWordResponseDTO;
import com.eriksgda.guessCat.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/start")
    public ResponseEntity<?> getRandomWord(){
        try{
            GameWordResponseDTO result = this.gameService.startNewGame();
            return ResponseEntity.ok().body(result);
        } catch (IOException | FileIsEmptyException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error.");
        }
    }

    @PostMapping("/done")
    public ResponseEntity<?> registerGameMatch(@RequestBody GameMatchDTO data){
        try{
            Cat currentPlayer = (Cat) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UUID playerId = currentPlayer.getId();

            GameResponseDTO result = this.gameService.registerGamePlayed(data, playerId);
            return ResponseEntity.ok().body(result);
        } catch (UserDoesNotExistException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error.");
        }
    }

}
