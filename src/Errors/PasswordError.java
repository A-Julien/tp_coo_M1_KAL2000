package Errors;

import java.io.Serializable;

public class PasswordError extends Exception implements Serializable {
    public PasswordError(String errorMessage) {
        super(errorMessage);
    }
}