package Exception;

import java.io.Serializable;

public class SubCardException extends Exception implements Serializable {
    public SubCardException(String errorMessage) {
        super(errorMessage);
    }
}