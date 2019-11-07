package Errors;

import java.io.Serializable;

public class CardError extends Exception implements Serializable {
    public CardError(String errorMessage) {
        super(errorMessage);
    }
}