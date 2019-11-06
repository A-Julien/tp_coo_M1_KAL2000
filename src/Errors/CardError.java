package Errors;

public class CardError extends Exception {
    public CardError(String errorMessage) {
        super(errorMessage);
    }
}