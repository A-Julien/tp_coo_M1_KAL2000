package org.com.Model.Exception;

import java.io.Serializable;

public class SubCardException extends Exception implements Serializable {
    public SubCardException(String errorMessage) {
        super(errorMessage);
    }
}