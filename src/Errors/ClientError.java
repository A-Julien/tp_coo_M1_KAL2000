package Errors;

import java.io.Serializable;

public class ClientError extends Exception implements Serializable {
    public ClientError(String errorMessage) {
        super(errorMessage);
    }
}