package com.eriksgda.guessCat.exceptions;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException() {
        super("User does nto exist.");
    }
}
