package pl.library.exceptions;

public class PublicationAlreadyExistException extends RuntimeException{
    public PublicationAlreadyExistException(String message) {
        super(message);
    }
}
