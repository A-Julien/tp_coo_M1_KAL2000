package Exception;

import java.io.Serializable;

public class RentException extends Exception implements Serializable {
    public RentException(String errorMessage) {
        super(errorMessage);
    }
}