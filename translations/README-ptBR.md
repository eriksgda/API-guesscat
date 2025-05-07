# GuessCAT Game [API]

![status](https://img.shields.io/badge/status-in%20development-orange?sty)
[![English](https://img.shields.io/badge/lang-en-blue?style=flat-square)](../README.md)
![license](https://img.shields.io/badge/license-MIT-blue)

## Descrição 

Este projeto é uma reinterpretação do site [Termo](https://term.ooo/). Ele fornece uma API RESTful backend construída com Spring Boot 
que gerencia todo o controle de usuários (criar, ler, atualizar, excluir), autenticação e autorização seguras via JWT 
e Spring Security, além da lógica principal do jogo — selecionando aleatoriamente uma palavra secreta a partir de um 
arquivo .txt local e registrando o histórico de cada partida do usuário. Os dados dos usuários são persistidos em um 
banco de dados PostgresSQL (executado em um contêiner Docker exclusivamente para o banco de dados), garantindo um 
armazenamento confiável de contas e históricos de partidas.

### Lógica do jogo:

Uma palavra aleatória é selecionada, e o jogador tem um número de tentativas igual ao comprimento da palavra para adivinhá-la.

## Tecnologias e Pré-requisitos

- Java | 17+
- Spring Boot | 3.3.4+
- Spring Data JPA | 3.3.4+
- Spring Security | 6.3.4+
- Java JWT | 4.4.0+
- Maven | 3.8+
- PostgreSQL | Latest (using docker)
- Docker | Latest

## Instalação

1.  Clone o repositório:
    ```bash
    git clone https://github.com/eriksgda/API-guesscat.git
    ```
2. Inicie o banco de dados:
    ```bash
    docker-compose up -d
    ```
3. Compile e execute a API:
    ```bash
    mvn clean springboot::run
    ```
4. Verifique:

   A API agora deve estar acessível em http://localhost:8080.

## Endpoints

### Autenticação

#### POST `cat/auth/register`
- Registra um novo usuário e retorna um token JWT.
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
- Autentica um usuário e retorna um token JWT.
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
- Atualiza conta do usuário.
- **Requer token JWT**
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
- Deleta conta do usuário.
- **Requer token JWT**
- **Response:**

```json
{
  "message": "User deleted successfully."
}
```

#### GET `cat/account/history`
- Retorna todos os jogos do histórico de partidas do usuário.
- **Requer token JWT**
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
- Retorna uma palavra aleatória.
- **Requer token JWT**
- **Response:**

```json
{
  "word": "randomWord"
}
```

#### POST `game/done`
-  Registra um novo jogo no histórico de partidas do usuário.
- **Requer token JWT**
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

## Licensa

Este projeto é licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.