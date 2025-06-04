package com.eriksgda.guessCat.services.implementations;

import com.eriksgda.guessCat.exceptions.FileIsEmptyException;
import com.eriksgda.guessCat.exceptions.UserDoesNotExistException;
import com.eriksgda.guessCat.model.cats.Cat;
import com.eriksgda.guessCat.model.game.Game;
import com.eriksgda.guessCat.model.game.GameMatchDTO;
import com.eriksgda.guessCat.model.game.GameResponseDTO;
import com.eriksgda.guessCat.model.game.GameWordResponseDTO;
import com.eriksgda.guessCat.repositories.CatRepository;
import com.eriksgda.guessCat.repositories.GameRepository;
import com.eriksgda.guessCat.services.GameService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GameServiceImpl implements GameService {

    public Random random;
    public CatRepository catRepository;
    public GameRepository gameRepository;

    @Value("${api.game.database.file}")
    private String file;

    @Autowired
    public GameServiceImpl(CatRepository catRepository, GameRepository gameRepository) {
        this.catRepository = catRepository;
        this.gameRepository = gameRepository;
    }

    @PostConstruct
    public void init(){
        this.random = new Random();
    }

    @Override
    public GameWordResponseDTO startNewGame() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file);

        if (inputStream == null) {
            throw new IOException("File does not exist: " + file);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> lines = reader.lines().toList();

            if(lines.isEmpty()) {
                throw new FileIsEmptyException();
            }

            int randomIndex = this.random.nextInt(lines.size());
            String word = lines.get(randomIndex);

            return new GameWordResponseDTO(word);
        }
    }

    @Override
    public GameResponseDTO registerGamePlayed(GameMatchDTO data, UUID playerId) {
        Optional<Cat> currentPlayer = this.catRepository.findById(playerId);

        if (currentPlayer.isPresent()) {
            Game gameMatch = Game.builder()
                    .player(currentPlayer.get())
                    .word(data.word())
                    .guesses(data.guesses())
                    .playedIn(data.playedIn()).build();

            Game savedGame = this.gameRepository.save(gameMatch);
            return GameResponseDTO.fromEntity(savedGame);
        }
        throw new UserDoesNotExistException();
    }
}
