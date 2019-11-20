package Exception;

import java.io.Serializable;

public class SystemException extends Exception implements Serializable {
    public SystemException(String errorMessage) {
        super(errorMessage);
    }
}