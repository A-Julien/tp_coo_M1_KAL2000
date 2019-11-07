package Exception;

import java.io.Serializable;

public class FilmException extends Exception implements Serializable {
    public FilmException(String errorMessage) {
        super(errorMessage);
    }
}