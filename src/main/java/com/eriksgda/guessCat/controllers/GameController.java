package com.eriksgda.guessCat.controllers;

import com.eriksgda.guessCat.exceptions.FileIsEmptyException;
import com.eriksgda.guessCat.exceptions.UserDoesNotExistException;
import com.eriksgda.guessCat.model.game.Game;
import com.eriksgda.guessCat.model.game.GameMatchDTO;
import com.eriksgda.guessCat.model.game.GameWordResponseDTO;
import com.eriksgda.guessCat.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("game")
public class GameController {

    @Autowired
    private GameService gameService;

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
            Game result = this.gameService.registerGamePlayed(data);
            return ResponseEntity.ok().body(result);
        } catch (UserDoesNotExistException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error.");
        }
    }

}
