package com.eriksgda.guessCat.exceptions;

public class FileIsEmptyException extends RuntimeException {
    public FileIsEmptyException() {
        super("File is Empty!");
    }
}
