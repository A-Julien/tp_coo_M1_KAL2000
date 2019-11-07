package Errors;

import java.io.Serializable;

public class RentError extends Exception implements Serializable {
    public RentError(String errorMessage) {
        super(errorMessage);
    }
}