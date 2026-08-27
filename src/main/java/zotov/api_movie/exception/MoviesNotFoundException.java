package zotov.api_movie.exception;

public class MoviesNotFoundException extends RuntimeException {

    public MoviesNotFoundException(String message) {
        super(message);
    }
}
