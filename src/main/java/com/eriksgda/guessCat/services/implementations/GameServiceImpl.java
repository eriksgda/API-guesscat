package com.eriksgda.guessCat.services.implementations;

import com.eriksgda.guessCat.exceptions.FileIsEmptyException;
import com.eriksgda.guessCat.model.historic.GameWordResponseDTO;
import com.eriksgda.guessCat.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private Random generateRandomNumber(){
        return new Random();
    }

    @Override
    public GameWordResponseDTO startNewGame() throws IOException {
        final String path = "src/main/java/com/eriksgda/guessCat/services/db_words.txt";
        Path path_ = Path.of(path);

        if (!Files.exists(path_)) {
            throw new IOException("File does not exist: " + path);
        }
        if (Files.size(path_) == 0) {
            throw new FileIsEmptyException();
        }
        long totalLines;
        try (Stream<String> lines = Files.lines(path_)) {
            totalLines = lines.count();
        }
        long randomLineIndex = this.generateRandomNumber().nextInt((int) totalLines);

        try (Stream<String> lines = Files.lines(path_)) {
            String word = lines.skip(randomLineIndex)
                    .findFirst()
                    .orElseThrow(() -> new IOException("Failed to read random word from file."));

            return new GameWordResponseDTO(word);
        }
    }
}
