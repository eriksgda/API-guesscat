CREATE TABLE guesses_table (
    game_id UUID NOT NULL,
    guess VARCHAR(255) NOT NULL,
    FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE
);