package Errors;

public class RentError extends Exception {
    public RentError(String errorMessage) {
        super(errorMessage);
    }
}