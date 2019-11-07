package Errors;

import java.io.Serializable;

public class SubCardError extends Exception implements Serializable {
    public SubCardError(String errorMessage) {
        super(errorMessage);
    }
}