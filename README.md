# GuessCAT Game [API]

![status](https://img.shields.io/badge/status-in%20development-orange?sty)
[![Portuguese](https://img.shields.io/badge/lang-pt-brightgreen?style=flat-square)](translations/README-ptBR.md)
![license](https://img.shields.io/badge/license-MIT-blue)

## Description

This project is a reimagining of the site [Termo](https://term.ooo/). It provides a backend RESTful API built with Spring Boot 
that handles all user management (create, read, update, delete), secure authentication and authorization via JWT and Spring 
Security, and the core game logic—randomly selecting a secret word from a local .txt file and recording each game’s 
history for the user. User data is persisted in PostgresSQL (running in a Docker container solely for the database), 
ensuring reliable storage of accounts and game histories.

### Game logic:

A random word is selected, and the player has a number of attempts equal to the word's length to guess it.

## Technologies and Prerequisites

- Java | 17+
- Spring Boot | 3.3.4+
- Spring Data JPA | 3.3.4+
- Spring Security | 6.3.4+
- Java JWT | 4.4.0+
- Maven | 3.8+
- PostgreSQL | Latest (using docker)
- Docker | Latest

## Installation

1.  Clone the repository:
    ```bash
    git clone https://github.com/eriksgda/API-guesscat.git
    ```
2. Start the database:
    ```bash
    docker-compose up -d
    ```
3. Build and run the API:
    ```bash
    mvn clean springboot::run
    ```
4. Verify:
    
    The API should now be accessible at http://localhost:8080.

## Endpoints

### Authentication

#### POST `cat/auth/register`
- Registers a new user and returns a JWT token.
- **Request Body:**

```json
{
  "username":"username",
  "password":"password123"
}
```
- **Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsIn..."
}
```

#### POST `cat/auth/login`
- Authenticates a user and returns a JWT token.
- **Request Body:**

```json
{
  "username":"username",
  "password":"password123"
}
```
- **Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsIn..."
}
```

### User Account

#### PATCH `cat/account/update`
- Update user account.
- **Requires JWT Token**
- **Request Body:**

```json
{
  "newUsername":"newUser",
  "newPassword":"newPassword"
}
```
- **Response:**

```json
{
  "message": "User updated successfully."
}
```

#### DELETE `cat/account/delete`
- Delete user account.
- **Requires JWT Token**
- **Response:**

```json
{
  "message": "User deleted successfully."
}
```

#### GET `cat/account/history`
- Retrieve all games from the user's match history.
- **Requires JWT Token**
- **Response:**

```json
{
  "username": "username",
  "matchHistory": [
    {
      "id": "UT2857RGI43057TG",
      "word": "randomWord",
      "guesses": ["guess1", "guess2", "..."],
      "playedIn": "2024-05-06T10:10:00"
    },
    {
      "id": "UT2857RGI43057TH",
      "word": "anotherWord",
      "guesses": ["guessA", "guessB", "..."],
      "playedIn": "2024-05-07T12:30:00"
    }
  ]
}
```

### Game

#### GET `game/start`
- Retrieve a random word.
- **Requires JWT Token**
- **Response:**

```json
{
  "word": "randomWord"
}
```

#### POST `game/done`
-  Records a new game in the user's match history.
- **Requires JWT Token**
- **Request Body:**

```json
{
  "word": "randomWord",
  "guesses": ["guess1", "guess2", "..."],
  "playedIn": "2024-05-06T10:10:00"
}
```
- **Response:**

```json
{
  "id": "UT2857RGI43057TG",
  "word": "randomWord",
  "guesses": ["guess1", "guess2", "..."],
  "playedIn": "2024-05-06T10:10:00"
}
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
